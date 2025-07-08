package italogvss.digitalshowcase.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.TemplateDTO;
import italogvss.digitalshowcase.mapper.TemplateMapper;
import italogvss.digitalshowcase.model.Template;
import italogvss.digitalshowcase.repositories.MediaRepository;
import italogvss.digitalshowcase.repositories.TemplateRepository;
import italogvss.digitalshowcase.repositories.UserRepository;

@Service
public class TemplateService {

    @Autowired
    TemplateRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MediaRepository mediaRepository;

    public TemplateDTO create(TemplateDTO dto) throws ResponseStatusException {
        
        Template entity = new Template();
        entity.setName(dto.name());
        entity.setRows(dto.rows());
        entity.setColumns(dto.columns());
        entity.setColor1(dto.color1());
        entity.setColor2(dto.color2());
        entity.setTextColor(dto.textColor());
        entity.setPriceColor(dto.priceColor());
        entity.setTranslucid(dto.isTranslucid());
        entity.setTranslucid(dto.translucid());
        entity.setStriped(dto.isStriped());
        entity.setTextSize(dto.textSize());
        entity.setTextPos(dto.textPos());
        entity.setPricePos(dto.pricePos());
        entity.setCipherPos(dto.cipherPos());
        entity.setCipher(dto.cipher());
        entity.setHaveImage(dto.haveImage());
        entity.setImagePos(dto.imagePos());
        entity.setImageSize(dto.imageSize());
        entity.setShowName(dto.showName());
        if(dto.image() != null){
            var image = mediaRepository.findById(dto.image())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Midia não encontrado"));
            entity.setImage(image);
        }
        entity.setHaveBackground(dto.haveBackground());
        if(dto.background() != null){
            var background = mediaRepository.findById(dto.background())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Midia não encontrado"));
            entity.setBackground(background);
        }
        entity.setHaveGrid(dto.haveGrid());
        entity.setGrid(dto.grid());
        entity.setGridColor(dto.gridColor());
        entity.setIsDefault(dto.isDefault());

        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
        entity.setUser(user);
        repository.save(entity);
        return TemplateMapper.toDTO(entity);
    }

    public List<TemplateDTO> findAll(String id) {
        Long userId = Long.valueOf(id);
        List<Template> templates = repository.findByUserId(userId);
        return TemplateMapper.toDTOList(templates);
    }

    public List<TemplateDTO> findAllDefault() {
        List<Template> templates = repository.findByIsDefault(true);
        return TemplateMapper.toDTOList(templates);
    }

    public TemplateDTO findById(String id) {
        var entity = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template não encontrado"));
        return TemplateMapper.toDTO(entity);
    }

    public TemplateDTO update(TemplateDTO dto) throws ResponseStatusException {
        var entity = repository.findById(dto.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template não encontrado"));

        if (dto.name() != null) entity.setName(dto.name());
        if (dto.rows() != 0) entity.setRows(dto.rows());
        if (dto.columns() != 0) entity.setColumns(dto.columns());
        if (dto.color1() != null) entity.setColor1(dto.color1());
        if (dto.color2() != null) entity.setColor2(dto.color2());
        if (dto.textColor() != null) entity.setTextColor(dto.textColor());
        if (dto.priceColor() != null) entity.setPriceColor(dto.priceColor());
        entity.setTranslucid(dto.isTranslucid());
        entity.setTranslucid(dto.translucid());
        entity.setStriped(dto.isStriped());
        if (dto.textSize() != 0) entity.setTextSize(dto.textSize());
        if (dto.textPos() != null) entity.setTextPos(dto.textPos());
        if (dto.pricePos() != null) entity.setPricePos(dto.pricePos());
        if (dto.cipherPos() != null) entity.setCipherPos(dto.cipherPos());
        if (dto.cipher() != null) entity.setCipher(dto.cipher());
        entity.setShowName(dto.showName());
        entity.setHaveImage(dto.haveImage());
        if (dto.imagePos() != 0) entity.setImagePos(dto.imagePos());
        if (dto.imageSize() != 0) entity.setImageSize(dto.imageSize());
        if (dto.image() != null){
            var image = mediaRepository.findById(dto.image())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Midia não encontrado"));
            entity.setImage(image);
        }
        entity.setHaveBackground(dto.haveBackground());
        if (dto.background() != null) {
            var background = mediaRepository.findById(dto.background())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Midia não encontrado"));
            entity.setBackground(background);
        };
        entity.setHaveGrid(dto.haveGrid());
        if (dto.grid() != null) entity.setGrid(dto.grid());
        if (dto.gridColor() != null) entity.setGridColor(dto.gridColor());

        var updatedEntity = repository.save(entity);
        return TemplateMapper.toDTO(updatedEntity);
    }

    public void delete(Long id) throws Exception {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template não encontrado"));
        repository.delete(entity);
    }
}
