package italogvss.digitalshowcase.DTO;

import jakarta.validation.constraints.NotNull;

public record FullTemplateDTO(
    Long id,
    @NotNull Long userId,
    @NotNull String name,
    @NotNull int rows,
    @NotNull int columns,
    @NotNull String color1,
    @NotNull String color2,
    @NotNull String textColor,
    @NotNull String priceColor,
    @NotNull boolean isTranslucid,
    @NotNull int translucid,
    @NotNull boolean isStriped,
    @NotNull int textSize,
    @NotNull String textPos,
    @NotNull String pricePos,
    @NotNull String cipherPos,
    @NotNull String cipher,
    @NotNull boolean haveImage,
    @NotNull int imagePos,
    @NotNull int imageSize,
    MediaDTO image,
    @NotNull boolean haveBackground,
    MediaDTO background,
    @NotNull boolean haveGrid,
    @NotNull String grid,
    @NotNull String gridColor) {

}
