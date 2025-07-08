package italogvss.digitalshowcase.DTO;

import java.util.List;

public record FullPlaylistItemDTO(
        String type,
        MediaDTO media,
        PriceTableDTO priceTable,
        FullTemplateDTO template,
        List<ProductDTO> products,
        List<PriceTableLayoutDTO> layouts,
        int order,
        int seconds) {

}
