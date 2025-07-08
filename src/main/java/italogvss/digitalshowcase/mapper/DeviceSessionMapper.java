package italogvss.digitalshowcase.mapper;

import java.util.List;
import java.util.stream.Collectors;

import italogvss.digitalshowcase.DTO.DeviceSessionDTO;
import italogvss.digitalshowcase.model.DeviceSession;

public class DeviceSessionMapper {

    public static DeviceSessionDTO toDTO(DeviceSession model) {
        if (model == null) {
            return null;
        }

        // Converte o status de ENUM para String
        String status = model.getStatus() == DeviceSession.Status.ONLINE ? "ONLINE" : "OFFLINE";

        // Converte a data de LocalDateTime para String (pode utilizar um formato
        // específico)
        String connectedAt = model.getConnectedAt().toString();
        String lastSeen = model.getLastSeen().toString();

        return new DeviceSessionDTO(
                model.getId(),
                model.getPlaylist().getId(), // Pega o ID da Playlist
                model.getDeviceId(),
                model.getName(),
                connectedAt,
                lastSeen,
                status);
    }

    public static List<DeviceSessionDTO> toDTOList(List<DeviceSession> modelList) {
        if (modelList == null) {
            return null;
        }

        return modelList.stream()
                .map(DeviceSessionMapper::toDTO) // Converte cada DeviceSession em DeviceSessionDTO
                .collect(Collectors.toList());
    }
}
