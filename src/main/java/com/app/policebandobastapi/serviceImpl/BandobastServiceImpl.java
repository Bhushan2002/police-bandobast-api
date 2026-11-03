
package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.policebandobastapi.dto.BandobastRequestDto;
import com.app.policebandobastapi.dto.BandobastResponseDto;
import com.app.policebandobastapi.enums.Enums.BandobastStatus;
import com.app.policebandobastapi.enums.Enums.Role;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.Bandobast;
import com.app.policebandobastapi.repository.BandobastRepository;
import com.app.policebandobastapi.service.BandobastService;

@Service
@Transactional
public class BandobastServiceImpl implements BandobastService {

    private final BandobastRepository repo;

    // Constructor injection
    public BandobastServiceImpl(BandobastRepository repo) {
        this.repo = repo;
    }

    // 1. Create new Bandobast
    @Override
    public BandobastResponseDto createBandobast(
            BandobastRequestDto bandobastDto) {

        validateBandobast(bandobastDto);

        Bandobast bandobast = new Bandobast();

        bandobast.setTitle(bandobastDto.getTitle().trim());
        bandobast.setTypeId(bandobastDto.getTypeId());
        bandobast.setStartDateTime(bandobastDto.getStartDateTime());
        bandobast.setEndDateTime(bandobastDto.getEndDateTime());
        bandobast.setApprovalAuthorityId(
                bandobastDto.getApprovalAuthorityId());
        bandobast.setRequiredPersonnel(
                bandobastDto.getRequiredPersonnel());
        bandobast.setLocation(bandobastDto.getLocation().trim());
        bandobast.setLatitude(bandobastDto.getLatitude());
        bandobast.setLongitude(bandobastDto.getLongitude());
        bandobast.setInstructionTemplateId(
                bandobastDto.getInstructionTemplateId());
        bandobast.setSpecialInstructions(
                bandobastDto.getSpecialInstructions());

        // New Bandobast starts with PENDING status
        bandobast.setStatus(BandobastStatus.PENDING);

        // Temporary default until login/security integration
        bandobast.setCreatedByRole(Role.POLICE_STATION);

        Bandobast savedBandobast = repo.save(bandobast);

        return toDto(savedBandobast);
    }

    // 2. Get all Bandobast records
    @Override
    @Transactional(readOnly = true)
    public List<BandobastResponseDto> getAllBandobast() {

        return repo.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    // 3. Find Bandobast by ID
    @Override
    @Transactional(readOnly = true)
    public BandobastResponseDto findById(Long id) {

        Bandobast bandobast = getExisting(id);

        return toDto(bandobast);
    }

    // 4. Update existing Bandobast
    @Override
    public BandobastResponseDto updateBandobast(
            Long id, BandobastRequestDto bandobastDto) {

        Bandobast existing = getExisting(id);

        // Only pending Bandobast can be updated
        if (existing.getStatus() != BandobastStatus.PENDING) {
            throw new BadRequestException(
                    "Only pending Bandobast can be updated");
        }

        validateBandobast(bandobastDto);

        existing.setTitle(bandobastDto.getTitle().trim());
        existing.setTypeId(bandobastDto.getTypeId());
        existing.setStartDateTime(bandobastDto.getStartDateTime());
        existing.setEndDateTime(bandobastDto.getEndDateTime());
        existing.setApprovalAuthorityId(
                bandobastDto.getApprovalAuthorityId());
        existing.setRequiredPersonnel(
                bandobastDto.getRequiredPersonnel());
        existing.setLocation(bandobastDto.getLocation().trim());
        existing.setLatitude(bandobastDto.getLatitude());
        existing.setLongitude(bandobastDto.getLongitude());
        existing.setInstructionTemplateId(
                bandobastDto.getInstructionTemplateId());
        existing.setSpecialInstructions(
                bandobastDto.getSpecialInstructions());

        Bandobast updatedBandobast = repo.save(existing);

        return toDto(updatedBandobast);
    }

    // 5. Delete Bandobast
    @Override
    public void deleteBandobast(Long id) {

        Bandobast existing = getExisting(id);

        // Do not delete approved or completed records
        if (existing.getStatus() != BandobastStatus.PENDING) {
            throw new BadRequestException(
                    "Only pending Bandobast can be deleted");
        }

        repo.delete(existing);
    }

    // Find existing Bandobast
    private Bandobast getExisting(Long id) {

        if (id == null || id <= 0) {
            throw new BadRequestException(
                    "Invalid Bandobast ID");
        }

        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bandobast", id));
    }

    // Validate Bandobast request
    private void validateBandobast(BandobastRequestDto dto) {

        if (dto == null) {
            throw new BadRequestException(
                    "Bandobast details are required");
        }

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new BadRequestException(
                    "Bandobast title is required");
        }

        if (dto.getTypeId() == null || dto.getTypeId() <= 0) {
            throw new BadRequestException(
                    "Valid Bandobast type is required");
        }

        if (dto.getStartDateTime() == null ||
                dto.getEndDateTime() == null) {
            throw new BadRequestException(
                    "Start date and end date are required");
        }

        if (!dto.getEndDateTime().isAfter(dto.getStartDateTime())) {
            throw new BadRequestException(
                    "End date and time must be after start date and time");
        }

        if (dto.getApprovalAuthorityId() == null ||
                dto.getApprovalAuthorityId() <= 0) {
            throw new BadRequestException(
                    "Valid approval authority is required");
        }

        if (dto.getRequiredPersonnel() == null ||
                dto.getRequiredPersonnel() <= 0) {
            throw new BadRequestException(
                    "Required personnel must be greater than zero");
        }

        if (dto.getLocation() == null ||
                dto.getLocation().isBlank()) {
            throw new BadRequestException(
                    "Bandobast location is required");
        }
    }

    // Convert Entity into Response DTO
    private BandobastResponseDto toDto(Bandobast bandobast) {

        BandobastResponseDto dto = new BandobastResponseDto();

        dto.setId(bandobast.getId());
        dto.setOrderNo(bandobast.getOrderNo());
        dto.setTitle(bandobast.getTitle());
        dto.setStartDateTime(bandobast.getStartDateTime());
        dto.setEndDateTime(bandobast.getEndDateTime());
        dto.setRequiredPersonnel(bandobast.getRequiredPersonnel());
        dto.setStatus(bandobast.getStatus());
        dto.setCreatedAt(bandobast.getCreatedAt());
        dto.setDecidedAt(bandobast.getDecidedAt());
        dto.setDecisionRemark(bandobast.getDecisionRemark());

        return dto;
    }
}
