package italogvss.digitalshowcase.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.ProductDTO;
import italogvss.digitalshowcase.services.ProductService;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService service;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @PostMapping("create")
    public ResponseEntity<Object> createProduct(@RequestBody @Valid ProductDTO body) {
        return ResponseEntity.ok(service.create(body));
    }

    @PostMapping("createall")
    public ResponseEntity<Object> createProduct(@RequestBody @Valid List<ProductDTO> body) {        
        return ResponseEntity.ok(service.createAll(body));
    }

    @GetMapping("findall/{id}")
    public ResponseEntity<Object> findAll(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findAll(id));
    }

    @GetMapping("findbypricetableid/{id}")
    public ResponseEntity<Object> findProductsByPriceTableId(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findAllByPriceTableId(id));
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Object> find(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping("update")
    public ResponseEntity<List<ProductDTO>> update(@RequestBody @Valid List<ProductDTO> body) throws Exception {
        List<ProductDTO> updatedProducts = service.updateAll(body);
        return ResponseEntity.ok(updatedProducts);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestBody List<Long> ids) {
        try {
            service.deleteAll(ids);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

}
