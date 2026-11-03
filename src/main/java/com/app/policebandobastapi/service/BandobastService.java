
package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.BandobastRequestDto;
import com.app.policebandobastapi.dto.BandobastResponseDto;

public interface BandobastService {

    // Create new Bandobast
    public BandobastResponseDto createBandobast(BandobastRequestDto bandobast);

    // Get all Bandobast records
    public List<BandobastResponseDto> getAllBandobast();

    // Get Bandobast by ID
    public BandobastResponseDto findById(Long id);

    // Update Bandobast
    public BandobastResponseDto updateBandobast(Long id, BandobastRequestDto bandobast);

    // Delete Bandobast
    public void deleteBandobast(Long id);

}
