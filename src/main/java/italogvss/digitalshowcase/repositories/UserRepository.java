package italogvss.digitalshowcase.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import italogvss.digitalshowcase.model.User;
import italogvss.digitalshowcase.model.UserRole;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);

    List<User> findAllByOwnerId(Long id);

    Optional<User> findFirstByRole(UserRole admin);

    long countByIsActiveTrue();
}
