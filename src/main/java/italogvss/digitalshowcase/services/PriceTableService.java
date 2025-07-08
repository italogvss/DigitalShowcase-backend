package italogvss.digitalshowcase.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.PriceTableDTO;
import italogvss.digitalshowcase.model.PriceTable;
import italogvss.digitalshowcase.repositories.PriceTableRepository;
import italogvss.digitalshowcase.repositories.ProductRepository;
import italogvss.digitalshowcase.repositories.UserRepository;

@Service
public class PriceTableService {

    @Autowired
    PriceTableRepository repository;
    @Autowired
    ProductRepository prodRepository;
    @Autowired
    UserRepository userRepository;

    public PriceTableDTO create(PriceTableDTO dto) throws ResponseStatusException{
        PriceTable entity = new PriceTable();
        entity.setName(dto.name());
        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
        entity.setUser(user);
        repository.save(entity);
        return toDTO(entity);
    }

        public List<PriceTableDTO> findAll(String id) {
        Long userId = Long.valueOf(id);
        List<PriceTable> pricetable = repository.findByUserId(userId);
        return toDTOList(pricetable);
    }

    public PriceTableDTO findById(String id) {

        var entity = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tabela de Preço não encontrada"));
        return toDTO(entity);
    }

    public PriceTableDTO update(PriceTableDTO dto) throws ResponseStatusException {

        var entity = repository.findById(dto.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tabela de Preço não encontrada"));

        if (dto.name() != null)
            entity.setName(dto.name());

        var updatedEntity = repository.save(entity);
        return toDTO(updatedEntity);
    }

    public void delete(Long id) throws Exception {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tabela de Preço não encontrada"));
        repository.delete(entity);
    }

    // mapper
    public static PriceTableDTO toDTO(PriceTable o) {
        return new PriceTableDTO(o.getId(), o.getName(), o.getUser().getId());
    }

    public static List<PriceTableDTO> toDTOList(List<PriceTable> playlists) {
        List<PriceTableDTO> dtoList = new ArrayList<>();
        for (PriceTable playlist : playlists) {
            PriceTableDTO dto = toDTO(playlist);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
