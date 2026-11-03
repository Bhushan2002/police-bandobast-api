package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.DepartmentDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.DuplicateResourceException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.Department;
import com.app.policebandobastapi.repository.DepartmentRepo;
import com.app.policebandobastapi.service.DepartmentService;
import com.app.policebandobastapi.util.ValidationUtils;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo repo;

    @Override
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        String name = validateName(departmentDto);
        if (repo.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Department already exists with name " + name);
        }

        Department department = toEntity(departmentDto);
        department.setId(null); // let the database generate the id
        department.setName(name);
        if (department.getStatus() == null) {
            department.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(department));
    }

    @Override
    public List<DepartmentDto> getAllDepartment() {
        List<Department> departments = repo.findAll();

        return departments.stream()
                .map(department -> toDto(department))
                .toList();
    }

    @Override
    public DepartmentDto findById(Long id) {
        return toDto(getExisting(id));
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        Department existing = getExisting(id);
        String name = validateName(departmentDto);
        if (repo.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new DuplicateResourceException("Department already exists with name " + name);
        }

        existing.setName(name);
        if (departmentDto.getStatus() != null) {
            existing.setStatus(departmentDto.getStatus());
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteDepartment(Long id) {
        Department existing = getExisting(id);
        repo.delete(existing);
    }

    private Department getExisting(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id));
    }

    private String validateName(DepartmentDto dto) {
        if (dto == null) {
            throw new BadRequestException("Department details are required");
        }
        ValidationUtils.requireText(dto.getName(), "Department name", 150);
        return dto.getName().trim();
    }

    private DepartmentDto toDto(Department department) {
        return new DepartmentDto(department.getId(), department.getName(), department.getStatus());
    }

    private Department toEntity(DepartmentDto dto) {
        return new Department(dto.getId(), dto.getName(), dto.getStatus());
    }
}
