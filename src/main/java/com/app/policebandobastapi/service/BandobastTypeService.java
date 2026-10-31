package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.BandobastTypeDto;

public interface BandobastTypeService {

    public BandobastTypeDto addBandobastType(BandobastTypeDto bandobastType);

    public List<BandobastTypeDto> getAllBandobastType();

    public BandobastTypeDto findById(int id);

    public BandobastTypeDto updateBandobastType(int id, BandobastTypeDto bandobastType);

    public void deleteBandobastType(int id);

}
