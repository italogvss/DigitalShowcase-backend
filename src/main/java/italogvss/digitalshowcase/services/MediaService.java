package italogvss.digitalshowcase.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.jcodec.api.JCodecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.MediaDTO;
import italogvss.digitalshowcase.Util.ThumbnailGenerator;
import italogvss.digitalshowcase.model.Media;
import italogvss.digitalshowcase.model.User;
import italogvss.digitalshowcase.repositories.MediaRepository;
import italogvss.digitalshowcase.repositories.UserRepository;

@Service
public class MediaService {

	@Value("${media.image.directory}")
	private String imageDir;
	@Value("${media.image.preview.directory}")
	private String imagePreviewDir;
	@Value("${media.video.preview.directory}")
	private String videoPreviewDir;
	@Value("${media.video.directory}")
	private String videoDir;
	@Value("${server.host}")
	private String serverHost;
	@Value("${server.port}")
	private String serverPort;

	private String baseUrl = "https://193.203.174.34/";

	@Autowired
	MediaRepository repository;

	@Autowired
	UserRepository userRepository;

	public ResponseEntity<Object> create(String userId, String desc, String sec, MultipartFile file)
			throws ResponseStatusException {
		if (file.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há conteúdo na requisição");
		}
		try {
			User user = userRepository.findById(Long.valueOf(userId))
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
			String baseURL = serverHost + "/";
			String uniqueFileName = generateUniqueFileName(file);
			Path filePath = saveFile(file, uniqueFileName);
			Path thumbnailPath = ThumbnailGenerator.of(filePath, uniqueFileName, imagePreviewDir, videoPreviewDir);
			System.out.println("Salvou o preview: "+thumbnailPath.toAbsolutePath());
			Media media = new Media();
			media.setDescription(desc);
			media.setSec(Integer.parseInt(sec));
			media.setType(file.getContentType());
			media.setUrl(baseURL + filePath.toString().replace("\\", "/"));
			media.setUrlPreview(baseURL + thumbnailPath.toString().replace("\\", "/"));
			media.setUser(user);
			media.setSize(file.getSize() / 1024);

			return ResponseEntity.ok().body(toDTO(repository.save(media)));

		} catch (JCodecException e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar o arquivo", e);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public void update(MediaDTO dto) throws ResponseStatusException {
		Media existingContent = repository.findById(dto.id())
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrou o conteúdo"));
		existingContent.setDescription(dto.desc());
		repository.save(existingContent);
	}

	public void delete(String id) throws ResponseStatusException {
    Media media = repository.findById(Long.valueOf(id))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrou o conteúdo"));

    try {
        // Remove a parte fixa da URL para obter o nome do arquivo
        String originalRelativePath = media.getUrl().replace(serverHost, ""); // ex: "media/video/arquivo.mp4"
        String fileName = Paths.get(originalRelativePath).getFileName().toString();

        // Define o diretório base a partir do tipo
        String directory;
        String previewDirectory;
        if (media.getType().toLowerCase().startsWith("image")) {
            directory = "/"+imageDir;
            previewDirectory = "/"+imagePreviewDir;
        } else if (media.getType().toLowerCase().startsWith("video")) {
            directory = "/"+videoDir;
            previewDirectory = "/"+videoPreviewDir;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de mídia desconhecido");
        }

        // Monta o caminho absoluto para o arquivo original
        Path originalPath = Paths.get(directory, fileName);
        Files.deleteIfExists(originalPath);

        // Se existir preview, faz o mesmo
        if (media.getUrlPreview() != null) {
					String baseFileName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
            Path previewPath = Paths.get(previewDirectory, baseFileName + ".jpeg"); // Aqui o fileName precisa ter a extensão jpeg
            Files.deleteIfExists(previewPath);
        }

        // Remove o registro da mídia do repositório
        repository.delete(media);
    } catch (IOException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Não conseguiu deletar o conteúdo", e);
    }
}

	public void deleteByUserId(String userId) throws ResponseStatusException {
		List<Media> mediaList = repository.findByUserId(Long.valueOf(userId));

		if (!mediaList.isEmpty()) {
			try {
				for (Media media : mediaList) {
					// Deletar o arquivo original
					Path originalPath = Paths.get(media.getUrl().replace(serverHost + ":" + serverPort + "/", ""));
					Files.deleteIfExists(originalPath);

					// Deletar a miniatura, se existir
					String thumbDir = media.getUrlPreview();
					if (thumbDir != null) {
						Path thumbPath = Paths.get(thumbDir.replace(serverHost + ":" + serverPort + "/", ""));
						Files.deleteIfExists(thumbPath);
					}

					// Deletar do repositório
					repository.delete(media);
				}
			} catch (IOException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar mídias do usuário", e);
			}
		}
	}

	public List<MediaDTO> findAll(String id) {
		return toDTOList(repository.findByUserId(Long.valueOf(id)));
	}

	// mapper
	public static MediaDTO toDTO(Media o) {
		return new MediaDTO(o.getId(), o.getUser().getId(), o.getDescription(), o.getType(), o.getSize(), o.getSec(),
				o.getUrl(),
				o.getUrlPreview());
	}

	public static List<MediaDTO> toDTOList(List<Media> playlists) {
		List<MediaDTO> dtoList = new ArrayList<>();
		for (Media playlist : playlists) {
			MediaDTO dto = toDTO(playlist);
			dtoList.add(dto);
		}
		return dtoList;
	}

	private boolean isVideo(MultipartFile file) {
		return "video/mp4".equals(file.getContentType());
	}

	private Path saveFile(MultipartFile file, String uniqueFileName) throws IOException {
		String directory = isVideo(file) ? videoDir : imageDir;
		File dir = new File(directory	);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Path path = Paths.get(directory, uniqueFileName);
		Files.write(path, file.getBytes());
		return path;
	}

	private String generateUniqueFileName(MultipartFile file) {
		String originalFileName = file.getOriginalFilename();
		String fileExtension = originalFileName != null ? FilenameUtils.getExtension(originalFileName) : "";
		return UUID.randomUUID().toString() + "." + fileExtension;
	}

}
