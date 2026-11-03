package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.RejectionTemplateDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.RejectionTemplate;
import com.app.policebandobastapi.repository.PoliceStationRepo;
import com.app.policebandobastapi.repository.RejectionTemplateRepo;
import com.app.policebandobastapi.service.RejectionTemplateService;
import com.app.policebandobastapi.util.ValidationUtils;

@Service
public class RejectionTemplateServiceImpl implements RejectionTemplateService {

    @Autowired
    private RejectionTemplateRepo repo;

    @Autowired
    private PoliceStationRepo policeStationRepo;

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
    public RejectionTemplateDto findById(Long id) {
        return toDto(getExisting(id));
    }

    @Override
    public RejectionTemplateDto updateRejectionTemplate(Long id, RejectionTemplateDto rejectionTemplateDto) {
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
    public void deleteRejectionTemplate(Long id) {
        RejectionTemplate existing = getExisting(id);
        repo.delete(existing);
    }

    private RejectionTemplate getExisting(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rejection template", id));
    }

    // policeStationId is optional: null means the template is available to all stations
    private void validate(RejectionTemplateDto dto) {
        if (dto == null) {
            throw new BadRequestException("Rejection template details are required");
        }
        ValidationUtils.requireText(dto.getTitle(), "Rejection template title", 200);
        if (dto.getType() == null) {
            throw new BadRequestException("Rejection template type is required");
        }
        ValidationUtils.optionalExists(policeStationRepo, dto.getPoliceStationId(), "Police station");
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
