package italogvss.digitalshowcase.WebScoket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import italogvss.digitalshowcase.DTO.RegisterDeviceDTO;
import italogvss.digitalshowcase.services.DeviceSessionService;

@Component
public class WebSocketEventListener {

    @Autowired
    private DeviceSessionService deviceSessionService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        Long userId = Long.parseLong(headerAccessor.getNativeHeader("userId").getFirst());
        Long playlistId = Long.parseLong(headerAccessor.getNativeHeader("playlistId").getFirst());
        String deviceId = headerAccessor.getNativeHeader("deviceId").getFirst();
        String sessionId = headerAccessor.getSessionId();

        // Continue com seu processo
        System.out.println("UserId: " + userId);
        System.out.println("PlaylistId: " + playlistId);
        System.out.println("DeviceId: " + deviceId);
        System.out.println("SessionId: " + sessionId);
        deviceSessionService
                .registerDevice(new RegisterDeviceDTO(sessionId, userId, deviceId, playlistId));

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();

        // Marcar dispositivo como OFFLINE
        deviceSessionService.markDeviceOffline(sessionId);
    }
}
