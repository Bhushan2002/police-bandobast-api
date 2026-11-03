
package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.DutyPointDto;
import com.app.policebandobastapi.dto.DutyPointResponseDto;
import com.app.policebandobastapi.service.DutyPointService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bandobast/{bandobastId}/points")
public class DutyPointController {

    private final DutyPointService dutyPointService;

    // Constructor injection
    public DutyPointController(DutyPointService dutyPointService) {
        this.dutyPointService = dutyPointService;
    }

    // Add new Duty Point
    @PostMapping
    public ResponseEntity<DutyPointResponseDto> addDutyPoint(
            @PathVariable Long bandobastId,
            @RequestBody DutyPointDto dutyPointDto) {

        DutyPointResponseDto response =
                dutyPointService.addDutyPoint(bandobastId, dutyPointDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get all Duty Points of a Bandobast
    @GetMapping
    public ResponseEntity<List<DutyPointResponseDto>> getAllDutyPoints(
            @PathVariable Long bandobastId) {

        List<DutyPointResponseDto> response =
                dutyPointService.getAllDutyPoints(bandobastId);

        return ResponseEntity.ok(response);
    }

    // Get Duty Point by ID
    @GetMapping("/{pointId}")
    public ResponseEntity<DutyPointResponseDto> findById(
            @PathVariable Long bandobastId,
            @PathVariable Long pointId) {

        DutyPointResponseDto response =
                dutyPointService.findById(bandobastId, pointId);

        return ResponseEntity.ok(response);
    }

    // Update Duty Point
    @PutMapping("/{pointId}")
    public ResponseEntity<DutyPointResponseDto> updateDutyPoint(
            @PathVariable Long bandobastId,
            @PathVariable Long pointId,
            @RequestBody DutyPointDto dutyPointDto) {

        DutyPointResponseDto response =
                dutyPointService.updateDutyPoint(
                        bandobastId, pointId, dutyPointDto);

        return ResponseEntity.ok(response);
    }

    // Delete Duty Point
    @DeleteMapping("/{pointId}")
    public ResponseEntity<Void> deleteDutyPoint(
            @PathVariable Long bandobastId,
            @PathVariable Long pointId) {

        dutyPointService.deleteDutyPoint(bandobastId, pointId);

        return ResponseEntity.noContent().build();
    }
}
