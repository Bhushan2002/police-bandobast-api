package com.app.policebandobastapi.controller;


import com.app.policebandobastapi.model.Shift;
import com.app.policebandobastapi.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shift")
public class ShiftController {
    @Autowired
    private ShiftService service;


    @GetMapping
    public List<Shift> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shift> findById(@PathVariable int id) {
        Optional<Shift> shift = service.findById(id);
        if (shift.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shift.get());
    }

    @PostMapping
    public ResponseEntity<Shift> save(@RequestBody Shift shift) {
        try {
            Shift saved = service.save(shift);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shift> update(@PathVariable int id, @RequestBody Shift shift) {
        try {
            Optional<Shift> updated = service.update(id, shift);
            if (updated.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated.get());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = service.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
