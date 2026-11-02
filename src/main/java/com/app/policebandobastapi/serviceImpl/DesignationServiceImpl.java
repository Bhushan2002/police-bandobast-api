package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.DesignationDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.DuplicateResourceException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.Designation;
import com.app.policebandobastapi.repository.DesignationRepo;
import com.app.policebandobastapi.service.DesignationService;

@Service
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    private DesignationRepo repo;

    @Override
    public DesignationDto addDesignation(DesignationDto designationDto) {
        String name = validateName(designationDto);
        if (repo.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Designation already exists with name " + name);
        }

        Designation designation = toEntity(designationDto);
        designation.setId(0); // let the database generate the id
        designation.setName(name);
        if (designation.getStatus() == null) {
            designation.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(designation));
    }

    @Override
    public List<DesignationDto> getAllDesignation() {
        List<Designation> designations = repo.findAll();

        return designations.stream()
                .map(designation -> toDto(designation))
                .toList();
    }

    @Override
    public DesignationDto findById(int id) {
        return toDto(getExisting(id));
    }

    @Override
    public DesignationDto updateDesignation(int id, DesignationDto designationDto) {
        Designation existing = getExisting(id);
        String name = validateName(designationDto);
        if (repo.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new DuplicateResourceException("Designation already exists with name " + name);
        }

        existing.setName(name);
        if (designationDto.getStatus() != null) {
            existing.setStatus(designationDto.getStatus());
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteDesignation(int id) {
        Designation existing = getExisting(id);
        repo.delete(existing);
    }

    private Designation getExisting(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Designation", id));
    }

    private String validateName(DesignationDto dto) {
        if (dto == null || dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("Designation name is required");
        }
        return dto.getName().trim();
    }

    private DesignationDto toDto(Designation designation) {
        return new DesignationDto(designation.getId(), designation.getName(), designation.getStatus());
    }

    private Designation toEntity(DesignationDto dto) {
        return new Designation(dto.getId(), dto.getName(), dto.getStatus());
    }
}
