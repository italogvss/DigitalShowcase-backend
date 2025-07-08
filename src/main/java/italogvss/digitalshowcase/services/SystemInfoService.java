package italogvss.digitalshowcase.services;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import italogvss.digitalshowcase.DTO.Info.AppInfoDTO;
import italogvss.digitalshowcase.DTO.Info.InfoDTO;
import italogvss.digitalshowcase.DTO.Info.SystemInfoDTO;
import italogvss.digitalshowcase.model.DeviceSession.Status;
import italogvss.digitalshowcase.repositories.DeviceSessionRepository;
import italogvss.digitalshowcase.repositories.MediaRepository;
import italogvss.digitalshowcase.repositories.PlaylistRepository;
import italogvss.digitalshowcase.repositories.PriceTableRepository;
import italogvss.digitalshowcase.repositories.UserRepository;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

@Service
public class SystemInfoService {

  @Autowired
  PriceTableRepository tableRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  MediaRepository midiaRepository;
  @Autowired
  PlaylistRepository playlistRepository;
  @Autowired
  DeviceSessionRepository deviceRepository;

  public InfoDTO getAllInfo() {
    var appinfo = getAppInfo();
    var sysinfo = getSysInfo();
    var dto = new InfoDTO(appinfo, sysinfo);
    return dto;
  }

  private AppInfoDTO getAppInfo() {

    // ***Usuarios
    long userCount = userRepository.count();
    long activeUserCunt = userRepository.countByIsActiveTrue();

    // *** Tabelas de preço
    long priceTableCount = tableRepository.count();

    // *** playlists
    long playlistCount = playlistRepository.count();

    // *** media
    long midiaCount = midiaRepository.count();
    long imageCount = midiaRepository.countByTypeStartingWith("image");
    long videoCount = midiaRepository.countByTypeStartingWith("video");
    long midiaSize = 0;
    try {
      midiaSize = getFolderSizeInMegabytes("/media");
    } catch (Exception e) {
      e.printStackTrace();
    }

    long deviceCount = deviceRepository.count();
    long onlineDeviceCount = deviceRepository.countByStatus(Status.ONLINE);

    return new AppInfoDTO(userCount, activeUserCunt, priceTableCount, playlistCount, midiaCount, imageCount, videoCount,
        midiaSize, deviceCount, onlineDeviceCount);
  }

  private SystemInfoDTO getSysInfo() {
    SystemInfo systemInfo = new SystemInfo();

    // CPU usage
    CentralProcessor processor = systemInfo.getHardware().getProcessor();
    long[] prevTicks = processor.getSystemCpuLoadTicks();
    try {
      Thread.sleep(1000); // Um segundo para calcular a carga de CPU
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    double cpuUsage = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;

    // Tempo de execução
    Long uptime = new SystemInfo().getOperatingSystem().getSystemUptime();

    // Memória disponível e em uso
    GlobalMemory memory = systemInfo.getHardware().getMemory();
    long memoryAvailable = memory.getAvailable();
    long memoryUsed = memory.getTotal() - memoryAvailable;
    long memoryAvailableMB = memoryAvailable / (1024 * 1024);
    long memoryUsedMB = memoryUsed / (1024 * 1024);

    // Retornar DTO
    return new SystemInfoDTO(cpuUsage, uptime, memoryAvailableMB, memoryUsedMB);
  }

  private long getFolderSizeInMegabytes(String pathStr) throws IOException {
    Path path = Paths.get(pathStr);
    final long[] sizeInBytes = { 0 };

    Files.walkFileTree(path, new SimpleFileVisitor<>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        sizeInBytes[0] += attrs.size();
        return FileVisitResult.CONTINUE;
      }
    });

    return sizeInBytes[0] / (1024 * 1024); // conversão direta para MB (long)
  }
}
