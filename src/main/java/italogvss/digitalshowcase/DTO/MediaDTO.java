package italogvss.digitalshowcase.DTO;

import jakarta.validation.constraints.NotNull;

public record MediaDTO(@NotNull Long id, @NotNull Long userId, @NotNull String desc, @NotNull String type, Long size, Integer sec, String url, String urlPreview) {

}
