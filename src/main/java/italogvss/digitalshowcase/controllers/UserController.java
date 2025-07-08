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

import italogvss.digitalshowcase.DTO.PasswordChangeRequest;
import italogvss.digitalshowcase.DTO.UserDTO;
import italogvss.digitalshowcase.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("create")
    public ResponseEntity<Object> createPlaylist(@RequestBody @Valid UserDTO body) {
        try {
            return ResponseEntity.ok().body(service.create(body));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }

    }

    @GetMapping("findall/{ownerId}")
    public ResponseEntity<Object> findAll(@PathVariable String ownerId) {
        try {
            return ResponseEntity.ok().body(service.findAll(Long.valueOf(ownerId)));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody UserDTO body) throws Exception {
        try {
            return ResponseEntity.ok().body(service.updateProtected(body));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PutMapping("updatePassword")
    public ResponseEntity<Object> updatePassword(@RequestBody PasswordChangeRequest body) throws Exception {
        try {
            service.updatePassword(body);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) throws Exception {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

}
