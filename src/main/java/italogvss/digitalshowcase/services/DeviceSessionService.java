package italogvss.digitalshowcase.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.DeviceSessionDTO;
import italogvss.digitalshowcase.DTO.RegisterDeviceDTO;
import italogvss.digitalshowcase.DTO.UpdateDeviceSessionNameDTO;
import italogvss.digitalshowcase.mapper.DeviceSessionMapper;
import italogvss.digitalshowcase.model.DeviceSession;
import italogvss.digitalshowcase.model.DeviceSession.Status;
import italogvss.digitalshowcase.model.Playlist;
import italogvss.digitalshowcase.model.User;
import italogvss.digitalshowcase.repositories.DeviceSessionRepository;

@Service
public class DeviceSessionService {

    @Autowired
    private DeviceSessionRepository deviceSessionRepository;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserService userService;

    public List<DeviceSession> findAllActive() {
        return deviceSessionRepository.findAll()
                .stream().filter(session -> session.getStatus() == Status.ONLINE)
                .collect(Collectors.toList());
    }

    public List<DeviceSessionDTO> findAll(String id) {
        Long userId = Long.valueOf(id);
        List<DeviceSession> sessions = deviceSessionRepository.findByUserId(userId);
        return DeviceSessionMapper.toDTOList(sessions);
    }

    public void updateName(UpdateDeviceSessionNameDTO dto){
        DeviceSession session = deviceSessionRepository.findById(dto.id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seção não encontrada"));
        session.setName(dto.name());
        deviceSessionRepository.save(session);
    }

    public DeviceSession registerDevice(RegisterDeviceDTO dto) {
        
        try {
            User user = userService.findById(dto.userId());

            Playlist playlist = playlistService.findById(dto.playlistId().toString());

            Optional<DeviceSession> existingSession = deviceSessionRepository.findByDeviceId(dto.deviceId());

            DeviceSession deviceSession;

            if (existingSession.isPresent()) {
                // Atualiza sessão existente
                deviceSession = existingSession.get();
                deviceSession.setConnectedAt(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
                deviceSession.setSessionId(dto.sessionId());
                deviceSession.setPlaylist(playlist);
                deviceSession.setLastSeen(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
                deviceSession.setStatus(DeviceSession.Status.ONLINE);
            } else {
                // Cria nova sessão
                deviceSession = new DeviceSession();
                deviceSession.setUser(user);
                deviceSession.setPlaylist(playlist);
                deviceSession.setDeviceId(dto.deviceId());
                deviceSession.setSessionId(dto.sessionId());
                deviceSession.setConnectedAt(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
                deviceSession.setLastSeen(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
                deviceSession.setStatus(DeviceSession.Status.ONLINE);
            }

            return deviceSessionRepository.save(deviceSession);

        } catch (ResponseStatusException ex) {
            // Lidar com a exceção aqui, você pode logar ou re-encaminhar a exceção
            throw ex; // Ou poderia fazer algum tratamento específico
        } catch (Exception ex) {
            // Para qualquer outra exceção não prevista
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar dispositivo", ex);
        }
    }

    public void markDeviceOffline(String deviceId) {
        Optional<DeviceSession> deviceSession = deviceSessionRepository.findBySessionId(deviceId);

        if (deviceSession.isPresent()) {
            DeviceSession session = deviceSession.get();
            session.setStatus(DeviceSession.Status.OFFLINE);
            session.setLastSeen(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
            deviceSessionRepository.save(session);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dispositivo não encontrado");
        }
    }

    public void delete(Long id) throws Exception {
        var entity = deviceSessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seção não encontrada"));
        System.out.println(entity);
        deviceSessionRepository.delete(entity);
    }
}
