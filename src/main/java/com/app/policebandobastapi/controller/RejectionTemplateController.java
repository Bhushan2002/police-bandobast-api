package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.RejectionTemplateDto;
import com.app.policebandobastapi.service.RejectionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/rejection-template")
public class RejectionTemplateController {
    @Autowired
    private RejectionTemplateService service;

    @GetMapping
    public List<RejectionTemplateDto> findAll() {
        return service.getAllRejectionTemplate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RejectionTemplateDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<RejectionTemplateDto> save(@RequestBody RejectionTemplateDto rejectionTemplate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addRejectionTemplate(rejectionTemplate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RejectionTemplateDto> update(@PathVariable Long id, @RequestBody RejectionTemplateDto rejectionTemplate) {
        return ResponseEntity.ok(service.updateRejectionTemplate(id, rejectionTemplate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRejectionTemplate(id);
        return ResponseEntity.noContent().build();
    }
}
