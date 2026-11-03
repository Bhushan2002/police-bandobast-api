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
import com.app.policebandobastapi.util.ValidationUtils;

@Service
public class BandobastTypeServiceImpl implements BandobastTypeService {

    @Autowired
    private BandobastTypeRepo repo;




    // save new bandobast
    @Override
    public BandobastTypeDto addBandobastType(BandobastTypeDto bandobastTypeDto) {
        String name = validateName(bandobastTypeDto);
        if (repo.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Bandobast type already exists with name " + name);
        }

        BandobastType bandobastType = toEntity(bandobastTypeDto);
        bandobastType.setId(null); // let the database generate the id
        bandobastType.setName(name);
        if (bandobastType.getStatus() == null) {
            bandobastType.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(bandobastType));
    }



    // get All Bandobast Types 
    @Override
    public List<BandobastTypeDto> getAllBandobastType() {
        List<BandobastType> bandobastTypes = repo.findAll();

        return bandobastTypes.stream()
                .map(bandobastType -> toDto(bandobastType))
                .toList();
    }

    @Override
    public BandobastTypeDto findById(Long id) {
        return toDto(getExisting(id));
    }

    @Override
    public BandobastTypeDto updateBandobastType(Long id, BandobastTypeDto bandobastTypeDto) {
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
    public void deleteBandobastType(Long id) {
        BandobastType existing = getExisting(id);
        if (existing.getStatus() == RecordStatus.INACTIVE) {
            throw new BadRequestException("Bandobast type with id " + id + " is already inactive");
        }
        existing.setStatus(RecordStatus.INACTIVE);
        repo.save(existing);
    }

    private BandobastType getExisting(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bandobast type", id));
    }

    private String validateName(BandobastTypeDto dto) {
        if (dto == null) {
            throw new BadRequestException("Bandobast type details are required");
        }
        ValidationUtils.requireText(dto.getName(), "Bandobast type name", 150);
        return dto.getName().trim();
    }

    private BandobastTypeDto toDto(BandobastType bandobastType) {
        return new BandobastTypeDto(bandobastType.getId(), bandobastType.getName(), bandobastType.getStatus());
    }

    private BandobastType toEntity(BandobastTypeDto dto) {
        return new BandobastType(dto.getId(), dto.getName(), dto.getStatus());
    }
}
