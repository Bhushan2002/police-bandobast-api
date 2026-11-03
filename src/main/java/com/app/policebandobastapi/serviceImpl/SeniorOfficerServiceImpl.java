package com.app.policebandobastapi.serviceImpl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.SeniorOfficerDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.DuplicateResourceException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.SeniorOfficer;
import com.app.policebandobastapi.repository.DepartmentRepo;
import com.app.policebandobastapi.repository.DesignationRepo;
import com.app.policebandobastapi.repository.SeniorOfficerRepo;
import com.app.policebandobastapi.service.SeniorOfficerService;
import com.app.policebandobastapi.util.ValidationUtils;

@Service
public class SeniorOfficerServiceImpl implements SeniorOfficerService {

    @Autowired
    private SeniorOfficerRepo repo;

    @Autowired
    private DesignationRepo designationRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public SeniorOfficerDto addSeniorOfficer(SeniorOfficerDto seniorOfficerDto) {
        validate(seniorOfficerDto);
        checkDuplicates(seniorOfficerDto, null);

        SeniorOfficer seniorOfficer = new SeniorOfficer();
        copyToEntity(seniorOfficerDto, seniorOfficer);
        if (seniorOfficer.getStatus() == null) {
            seniorOfficer.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(seniorOfficer));
    }

    @Override
    public List<SeniorOfficerDto> getAllSeniorOfficer() {
        List<SeniorOfficer> seniorOfficers = repo.findAll();

        return seniorOfficers.stream()
                .map(seniorOfficer -> toDto(seniorOfficer))
                .toList();
    }

    @Override
    public SeniorOfficerDto findById(Long id) {
        return toDto(getExisting(id));
    }

    @Override
    public SeniorOfficerDto updateSeniorOfficer(Long id, SeniorOfficerDto seniorOfficerDto) {
        SeniorOfficer existing = getExisting(id);
        validate(seniorOfficerDto);
        checkDuplicates(seniorOfficerDto, id);

        RecordStatus currentStatus = existing.getStatus();
        copyToEntity(seniorOfficerDto, existing);
        if (seniorOfficerDto.getStatus() == null) {
            existing.setStatus(currentStatus);
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteSeniorOfficer(Long id) {
        SeniorOfficer existing = getExisting(id);
        repo.delete(existing);
    }

    private SeniorOfficer getExisting(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Senior officer", id));
    }

    private void validate(SeniorOfficerDto dto) {
        if (dto == null) {
            throw new BadRequestException("Senior officer details are required");
        }
        ValidationUtils.requireText(dto.getName(), "Senior officer name", 150);
        ValidationUtils.requireExists(designationRepo, dto.getDesignationId(), "Designation");
        ValidationUtils.requireMobile(dto.getMobile(), "Mobile number");
        ValidationUtils.optionalEmail(dto.getEmail());
        ValidationUtils.optionalExists(departmentRepo, dto.getDepartmentId(), "Department");
        ValidationUtils.optionalText(dto.getServiceBookNo(), "Service book number", 50);
        ValidationUtils.optionalText(dto.getPost(), "Post", 150);
        if (dto.getAppointmentDateFrom() != null && dto.getAppointmentDateTo() != null
                && dto.getAppointmentDateTo().isBefore(dto.getAppointmentDateFrom())) {
            throw new BadRequestException("Appointment to date cannot be before appointment from date");
        }
    }

    // id = null on create, so the IdNot check is only used on update
    private void checkDuplicates(SeniorOfficerDto dto, Long id) {
        boolean mobileTaken = id == null ? repo.existsByMobile(dto.getMobile())
                : repo.existsByMobileAndIdNot(dto.getMobile(), id);
        if (mobileTaken) {
            throw new DuplicateResourceException("Senior officer already exists with mobile number " + dto.getMobile());
        }
    }

    // The entity still uses java.util.Date and the misspelled "emial" field; the DTO exposes LocalDate and "email"
    private void copyToEntity(SeniorOfficerDto dto, SeniorOfficer seniorOfficer) {
        seniorOfficer.setName(dto.getName().trim());
        seniorOfficer.setDesignationId(dto.getDesignationId());
        seniorOfficer.setMobile(dto.getMobile());
        seniorOfficer.setEmial(dto.getEmail() == null ? null : dto.getEmail().trim());
        seniorOfficer.setDepartmentId(dto.getDepartmentId());
        seniorOfficer.setServiceBookNo(dto.getServiceBookNo());
        seniorOfficer.setPost(dto.getPost());
        seniorOfficer.setAppointmentDateFrom(toDate(dto.getAppointmentDateFrom()));
        seniorOfficer.setAppointmentDateTo(toDate(dto.getAppointmentDateTo()));
        seniorOfficer.setStatus(dto.getStatus());
    }

    private SeniorOfficerDto toDto(SeniorOfficer seniorOfficer) {
        return new SeniorOfficerDto(seniorOfficer.getId(), seniorOfficer.getName(),
                seniorOfficer.getDesignationId(), seniorOfficer.getMobile(), seniorOfficer.getEmial(),
                seniorOfficer.getDepartmentId(), seniorOfficer.getServiceBookNo(), seniorOfficer.getPost(),
                toLocalDate(seniorOfficer.getAppointmentDateFrom()), toLocalDate(seniorOfficer.getAppointmentDateTo()),
                seniorOfficer.getStatus());
    }

    private Date toDate(LocalDate localDate) {
        return localDate == null ? null : java.sql.Date.valueOf(localDate);
    }

    private LocalDate toLocalDate(Date date) {
        return date == null ? null : new java.sql.Date(date.getTime()).toLocalDate();
    }
}
