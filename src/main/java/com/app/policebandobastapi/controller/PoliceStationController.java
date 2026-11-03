package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.PoliceStationDto;
import com.app.policebandobastapi.service.PoliceStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/police-station")
public class PoliceStationController {
    @Autowired
    private PoliceStationService service;

    @GetMapping
    public List<PoliceStationDto> findAll() {
        return service.getAllPoliceStation();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoliceStationDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PoliceStationDto> save(@RequestBody PoliceStationDto policeStation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addPoliceStation(policeStation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PoliceStationDto> update(@PathVariable Long id, @RequestBody PoliceStationDto policeStation) {
        return ResponseEntity.ok(service.updatePoliceStation(id, policeStation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletePoliceStation(id);
        return ResponseEntity.noContent().build();
    }
}
