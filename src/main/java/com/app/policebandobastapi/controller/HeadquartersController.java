package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.HeadquarterDto;
import com.app.policebandobastapi.service.HeadquarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/headquarter")
public class HeadquartersController {
    @Autowired
    private HeadquarterService service;

    @GetMapping
    public List<HeadquarterDto> findAll() {
        return service.getAllHeadquarter();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeadquarterDto> findById(@PathVariable int id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<HeadquarterDto> save(@RequestBody HeadquarterDto headquarter) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addHeadquarter(headquarter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HeadquarterDto> update(@PathVariable int id, @RequestBody HeadquarterDto headquarter) {
        return ResponseEntity.ok(service.updateHeadquarter(id, headquarter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteHeadquarter(id);
        return ResponseEntity.noContent().build();
    }
}
