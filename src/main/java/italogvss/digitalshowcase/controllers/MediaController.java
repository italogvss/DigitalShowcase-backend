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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.MediaDTO;
import italogvss.digitalshowcase.services.MediaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController()
@RequestMapping("file")
public class MediaController {

    @Autowired
    MediaService service;

    @PostMapping("create")
    public ResponseEntity<Object> create(@RequestParam String userId,@RequestParam String desc, @RequestParam String sec, @RequestParam("file") MultipartFile file) {
        try {
            return service.create(userId, desc, sec, file);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestBody @NotNull String id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
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

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody @Valid MediaDTO body) {
        try {
            service.update(body);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {

            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}

