package italogvss.digitalshowcase.mapper;

import java.util.List;
import java.util.stream.Collectors;

import italogvss.digitalshowcase.DTO.PriceTableDTO;
import italogvss.digitalshowcase.model.PriceTable;

public class PriceTableMapper {
    public static PriceTableDTO toDTO(PriceTable o) {
        return new PriceTableDTO(
                o.getId(),
                o.getName(),
                o.getUser().getId()
        );
    }

    public static List<PriceTableDTO> toDTOList(List<PriceTable> priceTableList) {
        return priceTableList.stream().map(PriceTableMapper::toDTO).collect(Collectors.toList());
    }
}
