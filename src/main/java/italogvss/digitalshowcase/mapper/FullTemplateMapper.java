package italogvss.digitalshowcase.mapper;

import italogvss.digitalshowcase.DTO.FullTemplateDTO;
import italogvss.digitalshowcase.model.Template;

public class FullTemplateMapper {
    public static FullTemplateDTO toDTO(Template template) {
        return new FullTemplateDTO(
                template.getId(),
                template.getUser().getId(),
                template.getName(),
                template.getRows(),
                template.getColumns(),
                template.getColor1(),
                template.getColor2(),
                template.getTextColor(),
                template.getPriceColor(),
                template.isTranslucid(),
                template.getTranslucid(),
                template.isStriped(),
                template.getTextSize(),
                template.getTextPos(),
                template.getPricePos(),
                template.getCipherPos(),
                template.getCipher(),
                template.isHaveImage(),
                template.getImagePos(),
                template.getImageSize(),
                template.getImage() != null ? MediaMapper.toDTO(template.getImage()) : null,
                template.isHaveBackground(),
                template.getBackground() != null ? MediaMapper.toDTO(template.getBackground()) : null,
                template.isHaveGrid(),
                template.getGrid(),
                template.getGridColor());
    }
}
