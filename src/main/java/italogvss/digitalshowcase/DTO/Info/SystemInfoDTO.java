package italogvss.digitalshowcase.DTO.Info;

public record SystemInfoDTO(
    double cpuUsage, 
    double uptime, 
    long memoryAvailable, 
    long memoryUsed) {
}
