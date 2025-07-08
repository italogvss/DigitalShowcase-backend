package italogvss.digitalshowcase.mapper;

import java.util.List;
import java.util.stream.Collectors;

import italogvss.digitalshowcase.DTO.TemplateDTO;
import italogvss.digitalshowcase.model.Template;

public class TemplateMapper {
    public static TemplateDTO toDTO(Template template) {
        return new TemplateDTO(
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
            template.isShowName(),
            template.isHaveImage(),
            template.getImagePos(),
            template.getImageSize(),
            template.getImage() != null ? template.getImage().getId() : null, 
            template.isHaveBackground(),
            template.getBackground() != null ? template.getBackground().getId() : null,
            template.isHaveGrid(),
            template.getGrid(),
            template.getGridColor(),
            template.getIsDefault()
        );
    }

    public static List<TemplateDTO> toDTOList(List<Template> templates) {
        return templates.stream().map(TemplateMapper::toDTO).collect(Collectors.toList());
    }
}

