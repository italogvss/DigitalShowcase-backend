package italogvss.digitalshowcase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import italogvss.digitalshowcase.model.PriceTable;
import italogvss.digitalshowcase.model.Product;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUserId(Long userId);
    List<Product> findByPricetable(PriceTable idPriceTable);

}
