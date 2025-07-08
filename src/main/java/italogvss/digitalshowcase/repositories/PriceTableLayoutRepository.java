package italogvss.digitalshowcase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import italogvss.digitalshowcase.model.PriceTableLayout;

public interface PriceTableLayoutRepository extends JpaRepository<PriceTableLayout, Long> {
  List<PriceTableLayout> findByPlaylistId(Long id);
}
