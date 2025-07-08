package italogvss.digitalshowcase.DTO;

import jakarta.validation.constraints.NotNull;

/* DONE */
public record AuthenticationDTO(@NotNull String username,@NotNull String password) {
}
