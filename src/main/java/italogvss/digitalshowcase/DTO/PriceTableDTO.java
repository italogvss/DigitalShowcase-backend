package italogvss.digitalshowcase.DTO;

import jakarta.validation.constraints.NotNull;


public record PriceTableDTO(Long id, @NotNull String name, @NotNull Long userId) {

}
