package com.app.policebandobastapi.controller;


import com.app.policebandobastapi.model.Designation;
import com.app.policebandobastapi.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/designation")
public class DesignationController {
    @Autowired
    private DesignationService service;


    @GetMapping
    public List<Designation> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Designation> findById(@PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Designation> save(@RequestBody Designation designation) {
        try {
            Designation saved = service.save(designation);
            return new ResponseEntity<Designation>(saved,HttpStatus.CREATED);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Designation> update(@PathVariable int id, @RequestBody Designation designation) {
        try {
            return service.update(id, designation)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
