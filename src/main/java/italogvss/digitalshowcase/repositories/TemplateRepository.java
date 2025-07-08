package italogvss.digitalshowcase.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import italogvss.digitalshowcase.model.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
     List<Template> findByUserId(Long userId);
     List<Template> findByIsDefault(Boolean isDefault);
}
