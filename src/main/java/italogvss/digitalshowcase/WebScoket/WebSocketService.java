package italogvss.digitalshowcase.WebScoket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  public void broadcastUserUpdate(String userId) {
    messagingTemplate.convertAndSend("/topic/user/" + userId, "reload");
  }

  public void broadcastPlaylistUpdate(String playlistId) {
    messagingTemplate.convertAndSend("/topic/playlist/" + playlistId, "reload");
  }
}
