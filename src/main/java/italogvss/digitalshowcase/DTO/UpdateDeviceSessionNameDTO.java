package italogvss.digitalshowcase.DTO;

import jakarta.validation.constraints.NotNull;

public record UpdateDeviceSessionNameDTO(
  @NotNull Long id,
  @NotNull String name
) {

}
