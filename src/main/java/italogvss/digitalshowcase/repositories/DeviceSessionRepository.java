package italogvss.digitalshowcase.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import italogvss.digitalshowcase.model.DeviceSession;
import italogvss.digitalshowcase.model.DeviceSession.Status;

@Repository
public interface DeviceSessionRepository extends JpaRepository<DeviceSession, Long> {
    Optional<DeviceSession> findBySessionId(String sessionId);

    Optional<DeviceSession> findByDeviceId(String deviceId);

    List<DeviceSession> findByUserId(Long userId);

    long countByStatus(Status status);
}
