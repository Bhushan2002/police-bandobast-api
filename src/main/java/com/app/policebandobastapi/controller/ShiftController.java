package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.ShiftDto;
import com.app.policebandobastapi.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/shift")
public class ShiftController {
    @Autowired
    private ShiftService service;

    @GetMapping
    public List<ShiftDto> findAll() {
        return service.getAllShift();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ShiftDto> save(@RequestBody ShiftDto shift) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addShift(shift));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftDto> update(@PathVariable Long id, @RequestBody ShiftDto shift) {
        return ResponseEntity.ok(service.updateShift(id, shift));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteShift(id);
        return ResponseEntity.noContent().build();
    }
}
