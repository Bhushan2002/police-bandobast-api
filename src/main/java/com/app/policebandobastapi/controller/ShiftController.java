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
    public ResponseEntity<List<Shift>> findAll() {
        try {

            if (service.findAll().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shift> findById(@PathVariable int id) {
        try {

            Optional<Shift> shift = service.findById(id);
            if (shift.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Shift>(shift.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Shift> save(@RequestBody Shift shift) {
        try {
            Shift saved = service.save(shift);
            return new ResponseEntity<Shift>(saved, HttpStatus.CREATED);

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
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            boolean deleted = service.delete(id);
            if (!deleted) {
                return new ResponseEntity<>("Shift not found", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
