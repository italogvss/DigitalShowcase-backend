package italogvss.digitalshowcase.DTO;

import jakarta.validation.constraints.NotNull;

public record PriceTableLayoutDTO(Long id, @NotNull Long playlistId,@NotNull Long priceTableId,@NotNull String html, @NotNull Integer part, @NotNull int order) {

}
