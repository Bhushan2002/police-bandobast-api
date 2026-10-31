package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.BandobastTypeDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.DuplicateResourceException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.BandobastType;
import com.app.policebandobastapi.repository.BandobastTypeRepo;
import com.app.policebandobastapi.service.BandobastTypeService;

@Service
public class BandobastTypeServiceImpl implements BandobastTypeService {

    @Autowired
    private BandobastTypeRepo repo;

    @Override
    public BandobastTypeDto addBandobastType(BandobastTypeDto bandobastTypeDto) {
        String name = validateName(bandobastTypeDto);
        if (repo.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Bandobast type already exists with name " + name);
        }

        BandobastType bandobastType = toEntity(bandobastTypeDto);
        bandobastType.setId(0); // let the database generate the id
        bandobastType.setName(name);
        if (bandobastType.getStatus() == null) {
            bandobastType.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(bandobastType));
    }

    @Override
    public List<BandobastTypeDto> getAllBandobastType() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public BandobastTypeDto findById(int id) {
        return toDto(getExisting(id));
    }

    @Override
    public BandobastTypeDto updateBandobastType(int id, BandobastTypeDto bandobastTypeDto) {
        BandobastType existing = getExisting(id);
        String name = validateName(bandobastTypeDto);
        if (repo.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new DuplicateResourceException("Bandobast type already exists with name " + name);
        }

        existing.setName(name);
        if (bandobastTypeDto.getStatus() != null) {
            existing.setStatus(bandobastTypeDto.getStatus());
        }
        return toDto(repo.save(existing));
    }

    // Soft delete: keeps the row so records that reference it stay valid
    @Override
    public void deleteBandobastType(int id) {
        BandobastType existing = getExisting(id);
        if (existing.getStatus() == RecordStatus.INACTIVE) {
            throw new BadRequestException("Bandobast type with id " + id + " is already inactive");
        }
        existing.setStatus(RecordStatus.INACTIVE);
        repo.save(existing);
    }

    private BandobastType getExisting(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bandobast type", id));
    }

    private String validateName(BandobastTypeDto dto) {
        if (dto == null || dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("Bandobast type name is required");
        }
        return dto.getName().trim();
    }

    private BandobastTypeDto toDto(BandobastType bandobastType) {
        return new BandobastTypeDto(bandobastType.getId(), bandobastType.getName(), bandobastType.getStatus());
    }

    private BandobastType toEntity(BandobastTypeDto dto) {
        return new BandobastType(dto.getId(), dto.getName(), dto.getStatus());
    }
}
