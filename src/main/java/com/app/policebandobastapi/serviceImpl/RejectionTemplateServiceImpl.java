package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.RejectionTemplateDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.RejectionTemplate;
import com.app.policebandobastapi.repository.RejectionTemplateRepo;
import com.app.policebandobastapi.service.RejectionTemplateService;

@Service
public class RejectionTemplateServiceImpl implements RejectionTemplateService {

    @Autowired
    private RejectionTemplateRepo repo;

    @Override
    public RejectionTemplateDto addRejectionTemplate(RejectionTemplateDto rejectionTemplateDto) {
        validate(rejectionTemplateDto);

        RejectionTemplate rejectionTemplate = new RejectionTemplate();
        copyToEntity(rejectionTemplateDto, rejectionTemplate);
        if (rejectionTemplate.getStatus() == null) {
            rejectionTemplate.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(rejectionTemplate));
    }

    @Override
    public List<RejectionTemplateDto> getAllRejectionTemplate() {
        List<RejectionTemplate> rejectionTemplates = repo.findAll();

        return rejectionTemplates.stream()
                .map(rejectionTemplate -> toDto(rejectionTemplate))
                .toList();
    }

    @Override
    public RejectionTemplateDto findById(int id) {
        return toDto(getExisting(id));
    }

    @Override
    public RejectionTemplateDto updateRejectionTemplate(int id, RejectionTemplateDto rejectionTemplateDto) {
        RejectionTemplate existing = getExisting(id);
        validate(rejectionTemplateDto);

        RecordStatus currentStatus = existing.getStatus();
        copyToEntity(rejectionTemplateDto, existing);
        if (rejectionTemplateDto.getStatus() == null) {
            existing.setStatus(currentStatus);
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteRejectionTemplate(int id) {
        RejectionTemplate existing = getExisting(id);
        repo.delete(existing);
    }

    private RejectionTemplate getExisting(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rejection template", id));
    }

    private void validate(RejectionTemplateDto dto) {
        if (dto == null || dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new BadRequestException("Rejection template title is required");
        }
        if (dto.getType() == null) {
            throw new BadRequestException("Rejection template type is required");
        }
    }

    private void copyToEntity(RejectionTemplateDto dto, RejectionTemplate rejectionTemplate) {
        rejectionTemplate.setTitle(dto.getTitle().trim());
        rejectionTemplate.setType(dto.getType());
        rejectionTemplate.setPoliceStationId(dto.getPoliceStationId());
        rejectionTemplate.setDescription(dto.getDescription());
        rejectionTemplate.setStatus(dto.getStatus());
    }

    private RejectionTemplateDto toDto(RejectionTemplate rejectionTemplate) {
        return new RejectionTemplateDto(rejectionTemplate.getId(), rejectionTemplate.getTitle(),
                rejectionTemplate.getType(), rejectionTemplate.getPoliceStationId(),
                rejectionTemplate.getDescription(), rejectionTemplate.getStatus());
    }
}
