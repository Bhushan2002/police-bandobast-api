package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.AppSettingsDto;
import com.app.policebandobastapi.service.AppSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Single settings row, so there is no id, list, create or delete
// Errors (not found, bad request) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/app-settings")
public class AppSettingsController {
    @Autowired
    private AppSettingsService service;

    @GetMapping
    public ResponseEntity<AppSettingsDto> get() {
        return ResponseEntity.ok(service.getSettings());
    }

    @PutMapping
    public ResponseEntity<AppSettingsDto> update(@RequestBody AppSettingsDto settings) {
        return ResponseEntity.ok(service.updateSettings(settings));
    }
}
