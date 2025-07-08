package italogvss.digitalshowcase.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "device_session")
public class DeviceSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY) 
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY) 
  @JoinColumn(name = "playlist_id", nullable = false)
  private Playlist playlist;

  @Column(nullable = false)
  private String deviceId;

  @Column(nullable = true)
  private String name;

  @Column(nullable = false)
  private String sessionId;

  @Column(name = "connected_at")
  private LocalDateTime connectedAt;

  @Column(name = "last_seen")
  private LocalDateTime lastSeen;

  @Enumerated(EnumType.STRING)
  private Status status;

  public enum Status {
    ONLINE, OFFLINE;
  }

  public DeviceSession(Long id, User user, Playlist playlist, String deviceId, String name, String sessionId,
      LocalDateTime connectedAt, LocalDateTime lastSeen, Status status) {
    this.id = id;
    this.user = user;
    this.name = name;
    this.playlist = playlist;
    this.deviceId = deviceId;
    this.sessionId = sessionId;
    this.connectedAt = connectedAt;
    this.lastSeen = lastSeen;
    this.status = status;
  }

  public DeviceSession() {
  }

  

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Playlist getPlaylist() {
    return playlist;
  }

  public void setPlaylist(Playlist playlist) {
    this.playlist = playlist;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public LocalDateTime getConnectedAt() {
    return connectedAt;
  }

  public void setConnectedAt(LocalDateTime connectedAt) {
    this.connectedAt = connectedAt;
  }

  public LocalDateTime getLastSeen() {
    return lastSeen;
  }

  public void setLastSeen(LocalDateTime lastSeen) {
    this.lastSeen = lastSeen;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    result = prime * result + ((playlist == null) ? 0 : playlist.hashCode());
    result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
    result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
    result = prime * result + ((connectedAt == null) ? 0 : connectedAt.hashCode());
    result = prime * result + ((lastSeen == null) ? 0 : lastSeen.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DeviceSession other = (DeviceSession) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    if (playlist == null) {
      if (other.playlist != null)
        return false;
    } else if (!playlist.equals(other.playlist))
      return false;
    if (deviceId == null) {
      if (other.deviceId != null)
        return false;
    } else if (!deviceId.equals(other.deviceId))
      return false;
    if (sessionId == null) {
      if (other.sessionId != null)
        return false;
    } else if (!sessionId.equals(other.sessionId))
      return false;
    if (connectedAt == null) {
      if (other.connectedAt != null)
        return false;
    } else if (!connectedAt.equals(other.connectedAt))
      return false;
    if (lastSeen == null) {
      if (other.lastSeen != null)
        return false;
    } else if (!lastSeen.equals(other.lastSeen))
      return false;
    if (status != other.status)
      return false;
    return true;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
