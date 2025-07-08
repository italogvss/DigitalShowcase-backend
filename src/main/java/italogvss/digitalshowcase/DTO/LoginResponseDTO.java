package italogvss.digitalshowcase.DTO;

import italogvss.digitalshowcase.model.UserRole;

public record LoginResponseDTO(Long id, String name, UserRole role, String token) {
}
