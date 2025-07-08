package italogvss.digitalshowcase.DTO;

import java.util.List;

public record FullPlaylistDTO(
  Long id,
  Long userId,
  String name,
  Integer isVertical,
  List<FullPlaylistItemDTO> items
) {

}
