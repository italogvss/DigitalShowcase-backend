package italogvss.digitalshowcase.DTO;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record PlaylistDTO(
  Long id,
  @NotNull Long userId,
  @NotNull String name,
  String code,
  @NotNull Integer isVertical,
  @NotNull boolean isTimeFixed,
  @NotNull boolean isTemplateFixed,
  @NotNull List<PlaylistItemDTO> items
) {

  @Override
  public String toString() {
      return "PlaylistDTO = [\n" +
              "  id: " + id + ",\n" +
              "  name: " + name + ",\n" +
              "  userId: " + userId + ",\n" +
              "  items: " + items + "\n" +
              "]";
  }

  
}
