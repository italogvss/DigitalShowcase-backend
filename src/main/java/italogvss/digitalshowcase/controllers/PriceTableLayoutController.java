package italogvss.digitalshowcase.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.PriceTableLayoutDTO;
import italogvss.digitalshowcase.services.PriceTableLayoutService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("priceTableLayout")
public class PriceTableLayoutController {

    @Autowired
    private PriceTableLayoutService service;

    @PostMapping("create")
    public ResponseEntity<Object> create(@RequestBody @Valid List<PriceTableLayoutDTO> body) {
        try {
            return ResponseEntity.ok().body(service.create(body));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody List<PriceTableLayoutDTO> body) {
        try {
          service.update(body);

            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping("findByPlaylist/{id}")
    public ResponseEntity<Object> findByPlaylist(@PathVariable String id) {
        try {
            return ResponseEntity.ok().body(service.findByPlaylistId(id));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}

