package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.DesignationDto;
import com.app.policebandobastapi.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/designation")
public class DesignationController {
    @Autowired
    private DesignationService service;

    @GetMapping
    public List<DesignationDto> findAll() {
        return service.getAllDesignation();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DesignationDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DesignationDto> save(@RequestBody DesignationDto designation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addDesignation(designation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DesignationDto> update(@PathVariable Long id, @RequestBody DesignationDto designation) {
        return ResponseEntity.ok(service.updateDesignation(id, designation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteDesignation(id);
        return ResponseEntity.noContent().build();
    }
}
