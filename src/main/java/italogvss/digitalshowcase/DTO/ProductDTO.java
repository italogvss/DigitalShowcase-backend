package italogvss.digitalshowcase.DTO;

import jakarta.validation.constraints.NotNull;

public record ProductDTO(Long id,@NotNull Long idProduct, @NotNull String name, @NotNull Float price, @NotNull Long idUser,Long priceTableId) {

}
