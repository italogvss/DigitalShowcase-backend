package italogvss.digitalshowcase.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.PlaylistDTO;
import italogvss.digitalshowcase.services.PlaylistService;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("playlist")
public class PlaylistController {

    @Autowired
    PlaylistService service;

    @PostMapping("create")
    public ResponseEntity<Object> createPlaylist(@RequestBody @Valid PlaylistDTO body) {
        try {
            return ResponseEntity.ok().body(service.create(body));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }

    }

    @GetMapping("findall/{id}")
    public ResponseEntity<Object> findAll(@PathVariable String id) {
        try {
            return ResponseEntity.ok().body(service.findAll(id));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping("findByCode/{code}")
    public ResponseEntity<Object> findByCode(@PathVariable String code) {
        try {
            return ResponseEntity.ok().body(service.findByCode(code));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody PlaylistDTO body) throws Exception {
        try {
            return ResponseEntity.ok().body(service.update(body));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestBody Long id) throws Exception {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

}
