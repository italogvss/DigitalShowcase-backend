package italogvss.digitalshowcase.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.ProductDTO;
import italogvss.digitalshowcase.model.Product;
import italogvss.digitalshowcase.repositories.PriceTableRepository;
import italogvss.digitalshowcase.repositories.ProductRepository;
import italogvss.digitalshowcase.repositories.UserRepository;
import jakarta.validation.Valid;

@Service
public class ProductService  {

    @Autowired
    ProductRepository repository;

    @Autowired
    PriceTableRepository tableRepository;

    @Autowired
    UserRepository userRepository;

    public ProductDTO create(@Valid ProductDTO dto) {
        var entity = new Product();
        entity.setIdProduct(dto.idProduct());
        entity.setName(dto.name());
        entity.setPrice(dto.price());
        var pt = tableRepository.findById(dto.priceTableId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PriceTable not found"));
        entity.setPricetable(pt);
        repository.save(entity);
        return dto;
    }

    public List<ProductDTO> createAll(List<ProductDTO> dtoList) {
        List<Product> entities = new ArrayList<>();

        for (ProductDTO dto : dtoList) {
            var entity = new Product();
            var user = userRepository.findById(dto.idUser())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            entity.setUser(user);
            entity.setIdProduct(dto.idProduct());
            entity.setName(dto.name());
            entity.setPrice(dto.price());
            entity.setPricetable(null);
            entities.add(entity);
        }
        return toDTOList(repository.saveAll(entities));
    }

    public List<ProductDTO> findAll(String id) {
        Long userId = Long.valueOf(id);
        List<Product> products = repository.findByUserId(userId);
        return toDTOList(products);
    }

    public List<ProductDTO> findAllByPriceTableId(String id) {
        Long priceTableId = Long.valueOf(id);
        var pt = tableRepository.findById(priceTableId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PriceTable not found"));
        List<Product> products = repository.findByPricetable(pt);
        return toDTOList(products);
    }

    public ProductDTO findById(String id) {

        var entity = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No records found for this ID"));
        return toDTO(entity);

    }

    public List<ProductDTO> updateAll(List<ProductDTO> dtos) {
        List<Product> productsToUpdate = new ArrayList<>();
        List<ProductDTO> updatedProductDTOs = new ArrayList<>();

        for (ProductDTO dto : dtos) {
            var entity = repository.findById(dto.id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "No records found for ID: " + dto.id()));

            if (dto.name() != null) {
                entity.setName(dto.name());
            }

            if (dto.price() != null && !dto.price().equals(entity.getPrice())) {
                entity.setPrice(dto.price());
            }

            if (dto.priceTableId() != null) {
                var pt = tableRepository.findById(dto.priceTableId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "No records found for ID: " + dto.priceTableId()));
                entity.setPricetable(pt);
            } else {
                entity.setPricetable(null);
            }
            productsToUpdate.add(entity);
        }

        // Salva todos os produtos atualizados
        List<Product> updatedEntities = repository.saveAll(productsToUpdate);

        // Converte as entidades atualizadas de volta para DTOs
        for (Product updatedEntity : updatedEntities) {
            updatedProductDTOs.add(toDTO(updatedEntity));
        }

        return updatedProductDTOs;
    }

    public void deleteAll(List<Long> ids) throws ResponseStatusException {
        for (Long id : ids) {
            var entity = repository.findById(id)
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Não foi possivel encontrar os produtos"));
            repository.delete(entity);
        }
    }

    private static ProductDTO toDTO(Product o) {
        return new ProductDTO(o.getId(), o.getIdProduct(), o.getName(), o.getPrice(),
                o.getUser().getId(), o.getPricetable() != null ? o.getPricetable().getId() : null);
    }

    private static List<ProductDTO> toDTOList(List<Product> playlists) {
        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product playlist : playlists) {
            ProductDTO dto = toDTO(playlist);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
