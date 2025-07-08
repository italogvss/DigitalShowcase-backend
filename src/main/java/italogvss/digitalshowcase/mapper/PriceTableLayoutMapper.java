package italogvss.digitalshowcase.mapper;

import java.util.List;
import java.util.stream.Collectors;

import italogvss.digitalshowcase.DTO.PriceTableLayoutDTO;
import italogvss.digitalshowcase.model.PriceTableLayout;

public class PriceTableLayoutMapper {

  public static PriceTableLayoutDTO toDTO(PriceTableLayout layout) {
    return new PriceTableLayoutDTO(
        layout.getId(),
        layout.getPlaylist().getId(),
        layout.getPriceTable().getId(),
        layout.getHtml(),
        layout.getPart(),
        layout.getOrder());
  }

  public static List<PriceTableLayoutDTO> toDTOList(List<PriceTableLayout> layoutList) {
    return layoutList.stream().map(PriceTableLayoutMapper::toDTO).collect(Collectors.toList());
  }
}
