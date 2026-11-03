
package com.app.policebandobastapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.app.policebandobastapi.dto.BandobastRequestDto;
import com.app.policebandobastapi.dto.BandobastResponseDto;
import com.app.policebandobastapi.service.BandobastService;

@RestController
@RequestMapping("/api/v1/bandobast")
@CrossOrigin(origins = "http://localhost")
public class BandobastController {

    private final BandobastService bandobastService;

    // Constructor injection
    public BandobastController(BandobastService bandobastService) {
        this.bandobastService = bandobastService;
    }

    // 1. Create new Bandobast
    @PostMapping
    public ResponseEntity<BandobastResponseDto> createBandobast(
            @RequestBody BandobastRequestDto bandobastDto) {

        BandobastResponseDto response =
                bandobastService.createBandobast(bandobastDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. Get all Bandobast records
    @GetMapping
    public ResponseEntity<List<BandobastResponseDto>> getAllBandobast() {

        List<BandobastResponseDto> response =
                bandobastService.getAllBandobast();

        return ResponseEntity.ok(response);
    }

    // 3. Get Bandobast by ID
    @GetMapping("/{id}")
    public ResponseEntity<BandobastResponseDto> findById(
            @PathVariable Long id) {

        BandobastResponseDto response =
                bandobastService.findById(id);

        return ResponseEntity.ok(response);
    }

    // 4. Update Bandobast
    @PutMapping("/{id}")
    public ResponseEntity<BandobastResponseDto> updateBandobast(
            @PathVariable Long id,
            @RequestBody BandobastRequestDto bandobastDto) {

        BandobastResponseDto response =
                bandobastService.updateBandobast(id, bandobastDto);

        return ResponseEntity.ok(response);
    }

    // 5. Delete Bandobast
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBandobast(
            @PathVariable Long id) {

        bandobastService.deleteBandobast(id);

        return ResponseEntity.noContent().build();
    }
}
