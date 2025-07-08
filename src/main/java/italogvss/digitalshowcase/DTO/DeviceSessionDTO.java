package italogvss.digitalshowcase.DTO;

public record DeviceSessionDTO(
  Long id,
  Long playlistId,
  String deviceId, 
  String name,
  String connectedAt,
  String lastSeen,
  String status
) {

}
