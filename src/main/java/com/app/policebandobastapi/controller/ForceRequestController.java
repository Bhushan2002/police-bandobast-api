
package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.ForceRequestDto;
import com.app.policebandobastapi.model.ForceRequest;
import com.app.policebandobastapi.service.ForceRequestService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bandobast/{bandobastId}/force-requests")
public class ForceRequestController {

    private final ForceRequestService forceRequestService;

    public ForceRequestController(ForceRequestService forceRequestService) {
        this.forceRequestService = forceRequestService;
    }

    // Create Force Request
    @PostMapping
    public ResponseEntity<ForceRequest> createForceRequest(
            @PathVariable Long bandobastId,
            @RequestBody ForceRequestDto dto) {

        ForceRequest response =
                forceRequestService.createForceRequest(bandobastId, dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get All Force Requests
    @GetMapping
    public ResponseEntity<List<ForceRequest>> getAllForceRequests(
            @PathVariable Long bandobastId) {

        List<ForceRequest> response =
                forceRequestService.getAllForceRequests(bandobastId);

        return ResponseEntity.ok(response);
    }

    // Get Force Request by ID
    @GetMapping("/{requestId}")
    public ResponseEntity<ForceRequest> findById(
            @PathVariable Long bandobastId,
            @PathVariable Long requestId) {

        ForceRequest response =
                forceRequestService.findById(bandobastId, requestId);

        return ResponseEntity.ok(response);
    }

    // Update Force Request
    @PutMapping("/{requestId}")
    public ResponseEntity<ForceRequest> updateForceRequest(
            @PathVariable Long bandobastId,
            @PathVariable Long requestId,
            @RequestBody ForceRequestDto dto) {

        ForceRequest response =
                forceRequestService.updateForceRequest(
                        bandobastId, requestId, dto);

        return ResponseEntity.ok(response);
    }

    // Delete Force Request
    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteForceRequest(
            @PathVariable Long bandobastId,
            @PathVariable Long requestId) {

        forceRequestService.deleteForceRequest(bandobastId, requestId);

        return ResponseEntity.noContent().build();
    }
}
