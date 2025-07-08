package italogvss.digitalshowcase.mapper;

import java.util.List;
import java.util.stream.Collectors;

import italogvss.digitalshowcase.DTO.ProductDTO;
import italogvss.digitalshowcase.model.Product;

public class ProductMapper {
    public static ProductDTO toDTO(Product o) {
        return new ProductDTO(
                o.getId(),
                o.getIdProduct(),
                o.getName(),
                o.getPrice(),
                o.getUser().getId(),
                o.getPricetable() != null ? o.getPricetable().getId() : null);
    }

    public static List<ProductDTO> toDTOList(List<Product> productList) {
        return productList.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }
}
