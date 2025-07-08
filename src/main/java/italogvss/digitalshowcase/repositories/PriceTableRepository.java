package italogvss.digitalshowcase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import italogvss.digitalshowcase.model.PriceTable;

public interface PriceTableRepository extends JpaRepository<PriceTable, Long> {
     List<PriceTable> findByUserId(Long userId);

}
