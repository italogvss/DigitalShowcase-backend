package italogvss.digitalshowcase.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.PriceTableLayoutDTO;
import italogvss.digitalshowcase.mapper.PriceTableLayoutMapper;
import italogvss.digitalshowcase.model.Playlist;
import italogvss.digitalshowcase.model.PriceTable;
import italogvss.digitalshowcase.model.PriceTableLayout;
import italogvss.digitalshowcase.repositories.PlaylistRepository;
import italogvss.digitalshowcase.repositories.PriceTableLayoutRepository;
import italogvss.digitalshowcase.repositories.PriceTableRepository;

@Service
public class PriceTableLayoutService {

  @Autowired
  private PriceTableLayoutRepository priceTableLayoutRepository;

  @Autowired
  private PlaylistRepository playlistRepository;

  @Autowired
  private PriceTableRepository priceTableRepository;

  // Cria um novo PriceTableLayout a partir do DTO informado
  public List<PriceTableLayoutDTO> create(List<PriceTableLayoutDTO> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista de PriceTableLayout não pode ser vazia");
    }

    // Obtém a Playlist com base no primeiro DTO (assumindo que todos têm o mesmo
    // playlistId)
    Playlist playlist = playlistRepository.findById(dtos.get(0).playlistId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada"));

    // Converte os DTOs para entidades validando a existência da PriceTable
    List<PriceTableLayout> layouts = dtos.stream().map(dto -> {
      PriceTable priceTable = priceTableRepository.findById(dto.priceTableId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PriceTable não encontrada"));

      return new PriceTableLayout(null, playlist, priceTable, dto.html(), dto.part(), dto.order());
    }).collect(Collectors.toList());

    // Salva todos os layouts de uma vez
    List<PriceTableLayout> savedLayouts = priceTableLayoutRepository.saveAll(layouts);

    // Converte para DTO e retorna
    return PriceTableLayoutMapper.toDTOList(savedLayouts);
  }

  // Atualiza os PriceTableLayout de uma Playlist: remove os existentes e insere
  // os novos
  public void update(List<PriceTableLayoutDTO> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista de PriceTableLayouts não pode ser vazia");
    }

    // Assume que todos os DTOs possuem o mesmo playlistId
    Long playlistId = dtos.get(0).playlistId();

    // Valida a existência da Playlist
    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrado"));

    // Remove os PriceTableLayouts existentes para a playlist
    List<PriceTableLayout> layoutsExistentes = priceTableLayoutRepository.findByPlaylistId(playlistId);
    priceTableLayoutRepository.deleteAll(layoutsExistentes);

    // Mapeia cada DTO para uma nova entidade, validando a existência da PriceTable
    List<PriceTableLayout> novosLayouts = dtos.stream().map(dto -> {
      PriceTable priceTable = priceTableRepository.findById(dto.priceTableId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PriceTable não encontrado"));
      PriceTableLayout layout = new PriceTableLayout();
      layout.setPlaylist(playlist);
      layout.setPriceTable(priceTable);
      layout.setHtml(dto.html());
      layout.setPart(dto.part());
      layout.setOrder(dto.order());
      return layout;
    }).collect(Collectors.toList());
    priceTableLayoutRepository.saveAll(novosLayouts);

  }

  // Retorna a lista de PriceTableLayoutDTOs associada a uma Playlist
  public List<PriceTableLayoutDTO> findByPlaylistId(String id) {
    Long playlistId;
    try {
      playlistId = Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID inválido");
    }

    List<PriceTableLayout> layouts = priceTableLayoutRepository.findByPlaylistId(playlistId);
    if (layouts.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrado");
    }
    return PriceTableLayoutMapper.toDTOList(layouts);
  }

}
