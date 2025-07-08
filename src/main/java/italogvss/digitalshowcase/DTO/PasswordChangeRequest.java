package italogvss.digitalshowcase.DTO;

import jakarta.validation.constraints.NotNull;

public record PasswordChangeRequest(@NotNull Long userId, @NotNull String newPassword) {

}
