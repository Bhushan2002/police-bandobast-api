package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.DigitalSignatureDto;
import com.app.policebandobastapi.service.DigitalSignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/digital-signature")
public class DigitalSignatureController {
    @Autowired
    private DigitalSignatureService service;

    @GetMapping
    public List<DigitalSignatureDto> findAll() {
        return service.getAllDigitalSignature();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigitalSignatureDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DigitalSignatureDto> save(@RequestBody DigitalSignatureDto signature) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addDigitalSignature(signature));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DigitalSignatureDto> update(@PathVariable Long id,
            @RequestBody DigitalSignatureDto signature) {
        return ResponseEntity.ok(service.updateDigitalSignature(id, signature));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteDigitalSignature(id);
        return ResponseEntity.noContent().build();
    }
}
