package italogvss.digitalshowcase.DTO;

import jakarta.validation.constraints.NotNull;

public record PlaylistItemDTO(
    Long id,
    @NotNull String type,
    Long media,
    Long priceTable,
    Long template,
    @NotNull int order,
    @NotNull int seconds,
    Long playlist) {

}
