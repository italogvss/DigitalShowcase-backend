package italogvss.digitalshowcase.mapper;

import java.util.List;
import java.util.stream.Collectors;

import italogvss.digitalshowcase.DTO.MediaDTO;
import italogvss.digitalshowcase.model.Media;

public class MediaMapper {
    public static MediaDTO toDTO(Media o) {
        return new MediaDTO(
                o.getId(),
                o.getUser().getId(),
                o.getDescription(),
                o.getType(),
                o.getSize(),
                o.getSec(),
                o.getUrl(),
                o.getUrlPreview()
        );
    }

    public static List<MediaDTO> toDTOList(List<Media> mediaList) {
        return mediaList.stream().map(MediaMapper::toDTO).collect(Collectors.toList());
    }
}

