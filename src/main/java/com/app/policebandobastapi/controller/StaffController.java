package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.StaffDto;
import com.app.policebandobastapi.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {
    @Autowired
    private StaffService service;

    @GetMapping
    public List<StaffDto> findAll() {
        return service.getAllStaff();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<StaffDto> save(@RequestBody StaffDto staff) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addStaff(staff));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffDto> update(@PathVariable Long id, @RequestBody StaffDto staff) {
        return ResponseEntity.ok(service.updateStaff(id, staff));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
