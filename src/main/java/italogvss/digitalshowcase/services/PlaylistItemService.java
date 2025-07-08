package italogvss.digitalshowcase.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.PlaylistItemDTO;
import italogvss.digitalshowcase.model.Playlist;
import italogvss.digitalshowcase.model.PlaylistItem;
import italogvss.digitalshowcase.repositories.MediaRepository;
import italogvss.digitalshowcase.repositories.PriceTableRepository;
import italogvss.digitalshowcase.repositories.TemplateRepository;
import italogvss.digitalshowcase.repositories.UserRepository;

@Service
public class PlaylistItemService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    MediaRepository mediaRepository;
    @Autowired
    PriceTableRepository priceTableRepository;

    public List<PlaylistItem> create(List<PlaylistItemDTO> dtos, Playlist playlist) throws ResponseStatusException {
        List<PlaylistItem> newList = new ArrayList<PlaylistItem>();
        for (PlaylistItemDTO dto : dtos) {
            PlaylistItem entity = new PlaylistItem();
            entity.setOrder(dto.order());
            entity.setSeconds(dto.seconds());
            entity.setType(dto.type());

            if ((dto.type().equals("image") || dto.type().equals("video"))) {
                if (dto.media() != null) {
                    var media = mediaRepository.findById(dto.media())
                            .orElseThrow(
                                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Media não encontrado"));
                    entity.setMedia(media);
                } else
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Media não encontrado");
            } else if (dto.type().equals("priceTable")) {
                if (dto.priceTable() != null) {
                    var pricetable = priceTableRepository.findById(dto.priceTable())
                            .orElseThrow(
                                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PriceTable não encontrado"));
                    entity.setPriceTable(pricetable);
                }else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PriceTable não encontrado");
            }
            if (dto.template() != null) {
                var template = templateRepository.findById(dto.template())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Media não encontrado"));
                entity.setTemplate(template);
            }
            entity.setPlaylist(playlist);
            
            newList.add(entity);
        }
        return newList;

    }

    /*
     * public List<PlaylistItemDTO> findAll(String id) {
     * Long userId = Long.valueOf(id);
     * List<PlaylistItem> PlaylistItems = repository.findByUserId(userId);
     * return toDTOList(PlaylistItems);
     * }
     */

    public PlaylistItemDTO update(PlaylistItemDTO dto) throws ResponseStatusException {
        return null;
        /*
         * var entity = repository.findById(dto.id())
         * .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
         * "PlaylistItem não encontrado"));
         * 
         * if (dto.name() != null) entity.setName(dto.name());
         * if (dto.rows() != 0) entity.setRows(dto.rows());
         * if (dto.columns() != 0) entity.setColumns(dto.columns());
         * if (dto.color1() != null) entity.setColor1(dto.color1());
         * if (dto.color2() != null) entity.setColor2(dto.color2());
         * if (dto.textColor() != null) entity.setTextColor(dto.textColor());
         * if (dto.priceColor() != null) entity.setPriceColor(dto.priceColor());
         * entity.setTranslucid(dto.isTranslucid());
         * entity.setTranslucid(dto.translucid());
         * entity.setStriped(dto.isStriped());
         * if (dto.textSize() != 0) entity.setTextSize(dto.textSize());
         * if (dto.textPos() != null) entity.setTextPos(dto.textPos());
         * if (dto.pricePos() != null) entity.setPricePos(dto.pricePos());
         * if (dto.cipherPos() != null) entity.setCipherPos(dto.cipherPos());
         * if (dto.cipher() != null) entity.setCipher(dto.cipher());
         * entity.setHaveImage(dto.haveImage());
         * if (dto.imagePos() != 0) entity.setImagePos(dto.imagePos());
         * if (dto.imageSize() != 0) entity.setImageSize(dto.imageSize());
         * if (dto.image() != null){
         * var image = mediaRepository.findById(dto.image())
         * .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
         * "Midia não encontrado"));
         * entity.setImage(image);
         * }
         * entity.setHaveBackground(dto.haveBackground());
         * if (dto.background() != null) {
         * var background = mediaRepository.findById(dto.background())
         * .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
         * "Midia não encontrado"));
         * entity.setBackground(background);
         * };
         * entity.setHaveGrid(dto.haveGrid());
         * if (dto.grid() != null) entity.setGrid(dto.grid());
         * if (dto.gridColor() != null) entity.setGridColor(dto.gridColor());
         * 
         * var updatedEntity = repository.save(entity);
         * return toDTO(updatedEntity);
         */
    }

    /*
     * public void delete(Long id) throws Exception {
     * var entity = repository.findById(id)
     * .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
     * "PlaylistItem não encontrado"));
     * repository.delete(entity);
     * }
     */

    // mapper
    public PlaylistItemDTO toDTO(PlaylistItem item) {
        return new PlaylistItemDTO(
                item.getId(),
                item.getType().toString(),
                item.getMedia() != null ? item.getMedia().getId() : null,
                item.getPriceTable() != null ? item.getPriceTable().getId() : null,
                item.getTemplate() != null ? item.getTemplate().getId() : null,
                item.getOrder(),
                item.getSeconds(),
                item.getPlaylist().getId());
    }

    public List<PlaylistItemDTO> toDTOList(List<PlaylistItem> playlists) {
        List<PlaylistItemDTO> dtoList = new ArrayList<>();
        for (PlaylistItem playlist : playlists) {
            PlaylistItemDTO dto = toDTO(playlist);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
