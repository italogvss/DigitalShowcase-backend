package italogvss.digitalshowcase.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.FullPlaylistDTO;
import italogvss.digitalshowcase.DTO.FullPlaylistItemDTO;
import italogvss.digitalshowcase.DTO.PlaylistDTO;
import italogvss.digitalshowcase.DTO.PlaylistItemDTO;
import italogvss.digitalshowcase.mapper.FullTemplateMapper;
import italogvss.digitalshowcase.mapper.MediaMapper;
import italogvss.digitalshowcase.mapper.PriceTableLayoutMapper;
import italogvss.digitalshowcase.mapper.PriceTableMapper;
import italogvss.digitalshowcase.mapper.ProductMapper;
import italogvss.digitalshowcase.model.Playlist;
import italogvss.digitalshowcase.model.PlaylistItem;
import italogvss.digitalshowcase.model.PriceTableLayout;
import italogvss.digitalshowcase.model.Product;
import italogvss.digitalshowcase.repositories.MediaRepository;
import italogvss.digitalshowcase.repositories.PlaylistRepository;
import italogvss.digitalshowcase.repositories.PriceTableLayoutRepository;
import italogvss.digitalshowcase.repositories.PriceTableRepository;
import italogvss.digitalshowcase.repositories.ProductRepository;
import italogvss.digitalshowcase.repositories.TemplateRepository;
import italogvss.digitalshowcase.repositories.UserRepository;

@Service
public class PlaylistService {

    @Autowired
    PlaylistRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    MediaRepository mediaRepository;
    @Autowired
    PriceTableRepository priceTableRepository;
    @Autowired
    PlaylistItemService playlistItemService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PriceTableLayoutRepository priceTableLayoutRepository;

    @Transactional
    public PlaylistDTO create(PlaylistDTO dto) throws ResponseStatusException {
        System.out.println(dto);
        Playlist entity = new Playlist();

        entity.setName(dto.name());
        entity.setTemplateFixed(dto.isTemplateFixed());
        entity.setTimeFixed(dto.isTimeFixed());
        entity.setVertical(dto.isVertical());
        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
        entity.setUser(user);
        var items = playlistItemService.create(dto.items(), entity);
        entity.setItems(items);
        return toDTO(repository.save(entity));
    }

    public List<PlaylistDTO> findAll(String id) {
        Long userId = Long.valueOf(id);
        List<Playlist> playlists = repository.findByUserId(userId);
        return toDTOList(playlists);
    }

    public FullPlaylistDTO findByCode(String code) {
        // Busca a playlist pelo código ou lança exceção se não encontrada
        Playlist playlist = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada"));

        List<FullPlaylistItemDTO> items = new ArrayList<>();
        // Recupera todos os layouts associados à playlist
        List<PriceTableLayout> layouts = priceTableLayoutRepository.findByPlaylistId(playlist.getId());

        for (PlaylistItem playlistItem : playlist.getItems()) {

            if (playlistItem.getType().startsWith("priceTable")) {
                if (playlistItem.getPriceTable() == null || playlistItem.getTemplate() == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "PriceTable deve ser informado para itens do tipo priceTable");
                }
                // Filtra os layouts que possuem o mesmo priceTableId do item
                List<PriceTableLayout> itemLayouts = layouts.stream()
                        .filter(layout -> layout.getOrder() == playlistItem.getOrder())
                        .collect(Collectors.toList());

                // Busca produtos relacionados ao PriceTable
                List<Product> products = productRepository.findByPricetable(playlistItem.getPriceTable());

                // Mapeia os dados para o DTO, adicionando os layouts correspondentes
                FullPlaylistItemDTO item = new FullPlaylistItemDTO(
                        playlistItem.getType(),
                        null, // Media é nulo para este tipo
                        PriceTableMapper.toDTO(playlistItem.getPriceTable()),
                        (playlistItem.getTemplate() != null) ? FullTemplateMapper.toDTO(playlistItem.getTemplate())
                                : null,
                        products.isEmpty() ? null : ProductMapper.toDTOList(products),
                        PriceTableLayoutMapper.toDTOList(itemLayouts),
                        playlistItem.getOrder(),
                        playlistItem.getSeconds());
                items.add(item);
            } else {
                // Para itens que não são do tipo "priceTable", espera-se que:
                // - media não seja nulo
                // - priceTable seja nulo
                if (playlistItem.getMedia() == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Media deve ser informado para itens que não são do tipo priceTable");
                }

                // Mapeia os dados para o DTO; não há produtos nem layouts para este tipo
                FullPlaylistItemDTO item = new FullPlaylistItemDTO(
                        playlistItem.getType(),
                        MediaMapper.toDTO(playlistItem.getMedia()),
                        null, // PriceTable é nulo
                        (playlistItem.getTemplate() != null) ? FullTemplateMapper.toDTO(playlistItem.getTemplate())
                                : null,
                        null, // Não há produtos, pois priceTable é nulo
                        null,
                        playlistItem.getOrder(),
                        playlistItem.getSeconds() // Layouts não se aplicam
                );
                items.add(item);
            }
        }
        // Retorna o DTO completo da playlist
        return new FullPlaylistDTO(playlist.getId(), playlist.getUser().getId(), playlist.getName(),
                playlist.isVertical(), items);
    }

    public Playlist findById(String id) {

        var entity = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tabela de Preço não encontrada"));
        return entity;
    }

    @Transactional
    public PlaylistDTO update(PlaylistDTO dto) throws ResponseStatusException {
        // 1. Buscar a Playlist existente
        Playlist existingPlaylist = repository.findById(dto.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada"));

        // 2. Atualizar campos básicos da Playlist
        existingPlaylist.setName(dto.name());
        existingPlaylist.setVertical(dto.isVertical());
        existingPlaylist.setTimeFixed(dto.isTimeFixed());
        existingPlaylist.setTemplateFixed(dto.isTemplateFixed());

        // 3. Processar os Itens da Playlist
        List<PlaylistItem> existingItems = existingPlaylist.getItems();
        Map<Long, PlaylistItem> existingItemsMap = existingItems.stream()
                .collect(Collectors.toMap(PlaylistItem::getId, Function.identity()));

        // IDs dos itens no DTO (excluindo novos itens sem ID)
        Set<Long> dtoItemIds = dto.items().stream()
                .map(PlaylistItemDTO::id)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Remover itens existentes que não estão no DTO
        existingItems.removeIf(item -> !dtoItemIds.contains(item.getId()));

        // Atualizar ou adicionar itens
        for (PlaylistItemDTO dtoItem : dto.items()) {
            Long itemId = dtoItem.id();
            if (itemId != null) {
                // Atualizar item existente
                PlaylistItem existingItem = existingItemsMap.get(itemId);
                if (existingItem == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Item da Playlist não encontrado: " + itemId);
                }

                // Atualizar campos básicos
                existingItem.setType(dtoItem.type());
                existingItem.setOrder(dtoItem.order());
                existingItem.setSeconds(dtoItem.seconds());

                // Verificar e atualizar Media, PriceTable e Template
                if (dtoItem.type().equals("image") || dtoItem.type().equals("video")) {
                    if (dtoItem.media() != null) {
                        var media = mediaRepository.findById(dtoItem.media())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Media não encontrado"));
                        existingItem.setMedia(media);
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Media não encontrado");
                    }
                } else if (dtoItem.type().equals("priceTable")) {
                    if (dtoItem.priceTable() != null) {
                        var priceTable = priceTableRepository.findById(dtoItem.priceTable())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "PriceTable não encontrado"));
                        existingItem.setPriceTable(priceTable);
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PriceTable não encontrado");
                    }
                }

                if (dtoItem.template() != null) {
                    var template = templateRepository.findById(dtoItem.template())
                            .orElseThrow(
                                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template não encontrado"));
                    existingItem.setTemplate(template);
                }
            } else {
                // Adicionar novo item
                PlaylistItem newItem = new PlaylistItem();
                newItem.setType(dtoItem.type());
                newItem.setOrder(dtoItem.order());
                newItem.setSeconds(dtoItem.seconds());
                newItem.setPlaylist(existingPlaylist);

                // Verificar e definir Media, PriceTable e Template para o novo item
                if (dtoItem.type().equals("image") || dtoItem.type().equals("video")) {
                    if (dtoItem.media() != null) {
                        var media = mediaRepository.findById(dtoItem.media())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Media não encontrado"));
                        newItem.setMedia(media);
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Media não encontrado");
                    }
                } else if (dtoItem.type().equals("priceTable")) {
                    if (dtoItem.priceTable() != null) {
                        var priceTable = priceTableRepository.findById(dtoItem.priceTable())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "PriceTable não encontrado"));
                        newItem.setPriceTable(priceTable);
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PriceTable não encontrado");
                    }
                }

                if (dtoItem.template() != null) {
                    var template = templateRepository.findById(dtoItem.template())
                            .orElseThrow(
                                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template não encontrado"));
                    newItem.setTemplate(template);
                }

                existingItems.add(newItem);
            }
        }

        // 4. Salvar a Playlist atualizada
        Playlist updatedPlaylist = repository.save(existingPlaylist);

        // 5. Converter para DTO e retornar
        return toDTO(updatedPlaylist);
    }

    public void delete(Long id) throws Exception {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrado"));
        repository.delete(entity);
    }

    // mapper
    public PlaylistDTO toDTO(Playlist playlist) {
        List<PlaylistItemDTO> newList = new ArrayList<>();
        for (PlaylistItem item : playlist.getItems()) {
            newList.add(new PlaylistItemDTO(
                    item.getId(),
                    item.getType().toString(),
                    item.getMedia() != null ? item.getMedia().getId() : null,
                    item.getPriceTable() != null ? item.getPriceTable().getId() : null,
                    item.getTemplate() != null ? item.getTemplate().getId() : null,
                    item.getOrder(),
                    item.getSeconds(), playlist.getId()));
        }
        return new PlaylistDTO(
                playlist.getId(),
                playlist.getUser().getId(),
                playlist.getName(),
                playlist.getCode(),
                playlist.isVertical(),
                playlist.isTimeFixed(),
                playlist.isTemplateFixed(),
                newList);
    }

    public List<PlaylistDTO> toDTOList(List<Playlist> playlists) {
        List<PlaylistDTO> dtoList = new ArrayList<>();
        for (Playlist playlist : playlists) {
            PlaylistDTO dto = toDTO(playlist);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
