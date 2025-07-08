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

import italogvss.digitalshowcase.DTO.PriceTableDTO;
import italogvss.digitalshowcase.services.PriceTableService;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("pricetable")
public class PriceTableController {

    @Autowired
    PriceTableService service;

    @PostMapping("create")
    public ResponseEntity<Object> createPriceTable(@RequestBody @Valid PriceTableDTO body) {
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

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody PriceTableDTO body) throws Exception {
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
