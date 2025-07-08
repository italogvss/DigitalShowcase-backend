package italogvss.digitalshowcase.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.jcodec.api.JCodecException;
import org.springframework.stereotype.Component;

import net.coobird.thumbnailator.Thumbnails;

@Component
public class ThumbnailGenerator {

    public static Path of(Path originalFilePath, String originalFileName, String imagePreviewDir,
            String videoPreviewDir)
            throws IOException, JCodecException {
        String extension = FilenameUtils.getExtension(originalFileName);
        System.out.println(imagePreviewDir);
        if ("mp4".equalsIgnoreCase(extension)) {
            checkDir(videoPreviewDir);
            return generateVideoThumbnail(originalFilePath, originalFileName, videoPreviewDir);
        } else {
            checkDir(imagePreviewDir);
            return generateImageThumbnail(originalFilePath, originalFileName, imagePreviewDir);
        }
    }

    private static Path generateVideoThumbnail(Path originalFilePath, String originalFileName, String videoPreviewDir)
            throws IOException {
        // Cria os objetos File para o vídeo e os arquivos temporários/resultantes.
        File videoFile = originalFilePath.toFile();
        File tempFile = new File(videoPreviewDir, UUID.randomUUID() + ".png");
        File outputFile = new File(videoPreviewDir, FilenameUtils.removeExtension(originalFileName) + ".jpeg");

        // Monta o comando FFmpeg para extrair um frame.
        // Aqui, "-ss 00:00:01.000" indica que o frame será capturado a 1 segundo do
        // vídeo,
        // e "-vframes 1" garante que apenas um único frame seja extraído.
        List<String> command = Arrays.asList(
                "ffmpeg",
                "-y", // sobrescreve o arquivo de destino, se existir
                "-i", videoFile.getAbsolutePath(), // arquivo de entrada
                "-ss", "00:00:01.000", // ponto de início para extrair o frame
                "-vframes", "1", // quantidade de frames a extrair (apenas 1)
                tempFile.getAbsolutePath() // arquivo de saída (imagem temporária)
        );

        // Cria e inicia o processo do FFmpeg
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true); // junta STDOUT e STDERR para facilitar o debug
        Process process = pb.start();

        // Aguarda o término do processo e verifica o exit code
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("FFmpeg retornou um código de erro: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // garante o status de interrompido
            throw new IOException("Processo de FFmpeg foi interrompido", e);
        }

        // Utiliza a biblioteca Thumbnails para redimensionar a imagem temporária para o
        // tamanho desejado (500x500)
        Thumbnails.of(tempFile)
                .size(500, 500)
                .outputFormat("jpeg")
                .toFile(outputFile);

        // Remove o arquivo temporário criado
        tempFile.delete();

        return outputFile.toPath();
    }

    private static Path generateImageThumbnail(Path originalFilePath, String originalFileName, String imagePreviewDir)
            throws IOException {
        // Use o construtor de File que recebe diretório e nome de arquivo separadamente
        File outputFile = new File(imagePreviewDir, FilenameUtils.removeExtension(originalFileName) + ".jpeg");
        Thumbnails.of(originalFilePath.toString())
                .size(500, 500)
                .outputFormat("jpeg")
                .toFile(outputFile);
        return outputFile.toPath();
    }

    private static void checkDir(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
