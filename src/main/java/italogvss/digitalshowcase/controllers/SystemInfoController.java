package italogvss.digitalshowcase.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import italogvss.digitalshowcase.DTO.ProductDTO;
import italogvss.digitalshowcase.services.SystemInfoService;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("sysinfo")
public class SystemInfoController {

  @Autowired
  SystemInfoService service;

@GetMapping()
    public ResponseEntity<Object> createProduct() {
        return ResponseEntity.ok(service.getAllInfo());
    }


}
