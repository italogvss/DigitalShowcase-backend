package italogvss.digitalshowcase.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import italogvss.digitalshowcase.model.Playlist;


public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
     List<Playlist> findByUserId(Long userId);
     Optional<Playlist> findByCode(String code);
}
