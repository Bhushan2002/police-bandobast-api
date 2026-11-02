package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.EmployeeDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.Employees;
import com.app.policebandobastapi.repository.EmployeesRepo;
import com.app.policebandobastapi.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeesRepo repo;

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        validate(employeeDto);

        Employees employee = new Employees();
        copyToEntity(employeeDto, employee);
        if (employee.getStatus() == null) {
            employee.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(employee));
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employees> employees = repo.findAll();

        return employees.stream()
                .map(employee -> toDto(employee))
                .toList();
    }

    @Override
    public EmployeeDto findById(int id) {
        return toDto(getExisting(id));
    }

    @Override
    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) {
        Employees existing = getExisting(id);
        validate(employeeDto);

        RecordStatus currentStatus = existing.getStatus();
        copyToEntity(employeeDto, existing);
        if (employeeDto.getStatus() == null) {
            existing.setStatus(currentStatus);
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteEmployee(int id) {
        Employees existing = getExisting(id);
        repo.delete(existing);
    }

    private Employees getExisting(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    private void validate(EmployeeDto dto) {
        if (dto == null || dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("Employee name is required");
        }
    }

    private void copyToEntity(EmployeeDto dto, Employees employee) {
        employee.setName(dto.getName().trim());
        employee.setDesignationId(dto.getDesignationId());
        employee.setBuckleNo(dto.getBuckleNo());
        employee.setPoliceStationId(dto.getPoliceStationId());
        employee.setDepartmentId(dto.getDepartmentId());
        employee.setForceCategory(dto.getForceCategory());
        employee.setStatus(dto.getStatus());
    }

    private EmployeeDto toDto(Employees employee) {
        return new EmployeeDto(employee.getId(), employee.getName(), employee.getDesignationId(),
                employee.getBuckleNo(), employee.getPoliceStationId(), employee.getDepartmentId(),
                employee.getForceCategory(), employee.getStatus());
    }
}
