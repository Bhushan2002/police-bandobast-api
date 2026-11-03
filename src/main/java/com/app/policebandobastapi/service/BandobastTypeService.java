package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.BandobastTypeDto;

public interface BandobastTypeService {

    public BandobastTypeDto addBandobastType(BandobastTypeDto bandobastType);

    public List<BandobastTypeDto> getAllBandobastType();

    public BandobastTypeDto findById(Long id);

    public BandobastTypeDto updateBandobastType(Long id, BandobastTypeDto bandobastType);

    public void deleteBandobastType(Long id);

}
