package italogvss.digitalshowcase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import italogvss.digitalshowcase.model.PlaylistItem;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItem, Long> {
}
