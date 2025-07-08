package italogvss.digitalshowcase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import italogvss.digitalshowcase.model.Media;

public interface MediaRepository extends JpaRepository<Media, Long> {
  List<Media> findByUserId(Long userId);
  long countByTypeStartingWith(String prefix);
}
