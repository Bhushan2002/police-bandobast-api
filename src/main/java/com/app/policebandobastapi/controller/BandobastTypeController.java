package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.BandobastTypeDto;
import com.app.policebandobastapi.service.BandobastTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/bandobast-type")
public class BandobastTypeController {
    @Autowired
    private BandobastTypeService service;

    @GetMapping
    public List<BandobastTypeDto> findAll() {
        return service.getAllBandobastType();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BandobastTypeDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<BandobastTypeDto> save(@RequestBody BandobastTypeDto bandobastType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addBandobastType(bandobastType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BandobastTypeDto> update(@PathVariable Long id, @RequestBody BandobastTypeDto bandobastType) {
        return ResponseEntity.ok(service.updateBandobastType(id, bandobastType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteBandobastType(id);
        return ResponseEntity.noContent().build();
    }
}
