package italogvss.digitalshowcase.DTO;

import java.time.LocalDate;

import italogvss.digitalshowcase.model.UserRole;
import jakarta.validation.constraints.NotNull;

public record UserDTO(@NotNull String name,
                      @NotNull String username,
                      @NotNull String password,
                      @NotNull UserRole role,
                      @NotNull Boolean isActive,
                      String cpf,
                      String telephone,
                      String email,
                      String city, 
                      String state,
                      String address,
                      LocalDate validUntil,
                      Long ownerId,
                      Long id) {
}
