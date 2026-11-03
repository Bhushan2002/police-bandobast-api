package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.InstructionTemplateDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.InstructionTemplate;
import com.app.policebandobastapi.repository.InstructionTemplateRepo;
import com.app.policebandobastapi.service.InstructionTemplateService;
import com.app.policebandobastapi.util.ValidationUtils;

@Service
public class InstructionTemplateServiceImpl implements InstructionTemplateService {

    @Autowired
    private InstructionTemplateRepo repo;

    @Override
    public InstructionTemplateDto addInstructionTemplate(InstructionTemplateDto instructionTemplateDto) {
        validate(instructionTemplateDto);

        InstructionTemplate instructionTemplate = new InstructionTemplate();
        copyToEntity(instructionTemplateDto, instructionTemplate);
        if (instructionTemplate.getStatus() == null) {
            instructionTemplate.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(instructionTemplate));
    }

    @Override
    public List<InstructionTemplateDto> getAllInstructionTemplate() {
        List<InstructionTemplate> instructionTemplates = repo.findAll();

        return instructionTemplates.stream()
                .map(instructionTemplate -> toDto(instructionTemplate))
                .toList();
    }

    @Override
    public InstructionTemplateDto findById(Long id) {
        return toDto(getExisting(id));
    }

    @Override
    public InstructionTemplateDto updateInstructionTemplate(Long id, InstructionTemplateDto instructionTemplateDto) {
        InstructionTemplate existing = getExisting(id);
        validate(instructionTemplateDto);

        RecordStatus currentStatus = existing.getStatus();
        copyToEntity(instructionTemplateDto, existing);
        if (instructionTemplateDto.getStatus() == null) {
            existing.setStatus(currentStatus);
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteInstructionTemplate(Long id) {
        InstructionTemplate existing = getExisting(id);
        repo.delete(existing);
    }

    private InstructionTemplate getExisting(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instruction template", id));
    }

    private void validate(InstructionTemplateDto dto) {
        if (dto == null) {
            throw new BadRequestException("Instruction template details are required");
        }
        ValidationUtils.requireText(dto.getTitle(), "Instruction template title", 200);
        if (dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new BadRequestException("Instruction template description is required");
        }
    }

    private void copyToEntity(InstructionTemplateDto dto, InstructionTemplate instructionTemplate) {
        instructionTemplate.setTitle(dto.getTitle().trim());
        instructionTemplate.setDescription(dto.getDescription());
        instructionTemplate.setStatus(dto.getStatus());
    }

    private InstructionTemplateDto toDto(InstructionTemplate instructionTemplate) {
        return new InstructionTemplateDto(instructionTemplate.getId(), instructionTemplate.getTitle(),
                instructionTemplate.getDescription(), instructionTemplate.getStatus());
    }
}
