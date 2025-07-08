package italogvss.digitalshowcase.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.UpdateDeviceSessionNameDTO;
import italogvss.digitalshowcase.WebScoket.WebSocketService;
import italogvss.digitalshowcase.services.DeviceSessionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController()
@RequestMapping("device-session")
public class DeviceSessionController {

    private final AccessDeniedHandler accessDeniedHandler;

  @Autowired
  DeviceSessionService service;

  @Autowired
  WebSocketService wsService;

    DeviceSessionController(AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

  @GetMapping("findall/{id}")
  public ResponseEntity<Object> findAll(@PathVariable String id) {
    try {
      return ResponseEntity.ok().body(service.findAll(id));
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }
  }

  @PutMapping("updateName")
  public ResponseEntity<Object> updateName(@RequestBody @Valid UpdateDeviceSessionNameDTO dto) {
    try {
      service.updateName(dto);
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }
    return ResponseEntity.ok().build();
  }

  @PutMapping("broadcastUpdate/{id}")
  public ResponseEntity<Object> broadcastUpdate(@PathVariable String id) {
    wsService.broadcastUserUpdate(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("broadcastPlaylist/{id}")
  public ResponseEntity<Object> broadcastPlaylist(@PathVariable String id) {
    wsService.broadcastPlaylistUpdate(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable String id) throws Exception {
    try {
      service.delete(Long.valueOf(id));
      return ResponseEntity.ok().build();
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }
  }
}
