package italogvss.digitalshowcase.DTO;

import jakarta.validation.constraints.NotNull;

public record TemplateDTO(
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
        @NotNull boolean showName,
        @NotNull boolean haveImage,
        @NotNull int imagePos,
        @NotNull int imageSize,
        Long image,
        @NotNull boolean haveBackground,
        Long background,
        @NotNull boolean haveGrid,
        @NotNull String grid,
        @NotNull String gridColor,
        @NotNull Boolean isDefault) {

    @Override
    public String toString() {
        return "TemplateDTO {\n" +
                "  id: " + id + ",\n" +
                "  userId: " + userId + ",\n" +
                "  name: " + name + ",\n" +
                "  rows: " + rows + ",\n" +
                "  columns: " + columns + ",\n" +
                "  color1: " + color1 + ",\n" +
                "  color2: " + color2 + ",\n" +
                "  textColor: " + textColor + ",\n" +
                "  priceColor: " + priceColor + ",\n" +
                "  isTranslucid: " + isTranslucid + ",\n" +
                "  translucid: " + translucid + ",\n" +
                "  isStriped: " + isStriped + ",\n" +
                "  textSize: " + textSize + ",\n" +
                "  textPos: " + textPos + ",\n" +
                "  pricePos: " + pricePos + ",\n" +
                "  cipherPos: " + cipherPos + ",\n" +
                "  cipher: " + cipher + ",\n" +
                "  haveImage: " + haveImage + ",\n" +
                "  imagePos: " + imagePos + ",\n" +
                "  imageSize: " + imageSize + ",\n" +
                "  image: " + image + ",\n" +
                "  haveBackground: " + haveBackground + ",\n" +
                "  background: " + background + ",\n" +
                "  haveGrid: " + haveGrid + ",\n" +
                "  grid: " + grid + ",\n" +
                "  gridColor: " + gridColor + "\n" +
                "}";
    }
}
