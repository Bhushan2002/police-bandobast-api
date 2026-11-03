package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.SeniorOfficerDto;
import com.app.policebandobastapi.service.SeniorOfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/senior-officer")
public class SeniorOfficerController {
    @Autowired
    private SeniorOfficerService service;

    @GetMapping
    public List<SeniorOfficerDto> findAll() {
        return service.getAllSeniorOfficer();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeniorOfficerDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SeniorOfficerDto> save(@RequestBody SeniorOfficerDto seniorOfficer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addSeniorOfficer(seniorOfficer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeniorOfficerDto> update(@PathVariable Long id, @RequestBody SeniorOfficerDto seniorOfficer) {
        return ResponseEntity.ok(service.updateSeniorOfficer(id, seniorOfficer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteSeniorOfficer(id);
        return ResponseEntity.noContent().build();
    }
}
