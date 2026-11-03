package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.HeadquarterDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.DuplicateResourceException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.Headquarter;
import com.app.policebandobastapi.repository.DepartmentRepo;
import com.app.policebandobastapi.repository.DesignationRepo;
import com.app.policebandobastapi.repository.HeadquarterRepo;
import com.app.policebandobastapi.service.HeadquarterService;
import com.app.policebandobastapi.util.ValidationUtils;

@Service
public class HeadquarterServiceImpl implements HeadquarterService {

    @Autowired
    private HeadquarterRepo repo;

    @Autowired
    private DesignationRepo designationRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public HeadquarterDto addHeadquarter(HeadquarterDto headquarterDto) {
        validate(headquarterDto);
        checkDuplicates(headquarterDto, null);

        Headquarter headquarter = new Headquarter();
        copyToEntity(headquarterDto, headquarter);
        if (headquarter.getStatus() == null) {
            headquarter.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(headquarter));
    }

    @Override
    public List<HeadquarterDto> getAllHeadquarter() {
        List<Headquarter> headquarters = repo.findAll();

        return headquarters.stream()
                .map(headquarter -> toDto(headquarter))
                .toList();
    }

    @Override
    public HeadquarterDto findById(Long id) {
        return toDto(getExisting(id));
    }

    @Override
    public HeadquarterDto updateHeadquarter(Long id, HeadquarterDto headquarterDto) {
        Headquarter existing = getExisting(id);
        validate(headquarterDto);
        checkDuplicates(headquarterDto, id);

        RecordStatus currentStatus = existing.getStatus();
        copyToEntity(headquarterDto, existing);
        if (headquarterDto.getStatus() == null) {
            existing.setStatus(currentStatus);
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteHeadquarter(Long id) {
        Headquarter existing = getExisting(id);
        repo.delete(existing);
    }

    private Headquarter getExisting(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Headquarter", id));
    }

    private void validate(HeadquarterDto dto) {
        if (dto == null) {
            throw new BadRequestException("Headquarter details are required");
        }
        ValidationUtils.requireText(dto.getName(), "Headquarter name", 200);
        ValidationUtils.requireExists(designationRepo, dto.getDesignationId(), "Designation");
        ValidationUtils.requireMobile(dto.getMobile(), "Mobile number");
        ValidationUtils.optionalEmail(dto.getEmail());
        ValidationUtils.optionalExists(departmentRepo, dto.getDepartmentId(), "Department");
        ValidationUtils.optionalText(dto.getServiceBookNo(), "Service book number", 50);
        ValidationUtils.optionalText(dto.getPost(), "Post", 150);
        if (dto.getAppointmentFrom() != null && dto.getAppointmentTo() != null
                && dto.getAppointmentTo().isBefore(dto.getAppointmentFrom())) {
            throw new BadRequestException("Appointment to date cannot be before appointment from date");
        }
    }

    // id = null on create, so the IdNot check is only used on update
    private void checkDuplicates(HeadquarterDto dto, Long id) {
        boolean mobileTaken = id == null ? repo.existsByMobile(dto.getMobile())
                : repo.existsByMobileAndIdNot(dto.getMobile(), id);
        if (mobileTaken) {
            throw new DuplicateResourceException("Headquarter already exists with mobile number " + dto.getMobile());
        }
    }

    private void copyToEntity(HeadquarterDto dto, Headquarter headquarter) {
        headquarter.setName(dto.getName().trim());
        headquarter.setDesignationId(dto.getDesignationId());
        headquarter.setMobile(dto.getMobile());
        headquarter.setEmail(dto.getEmail() == null ? null : dto.getEmail().trim());
        headquarter.setDepartmentId(dto.getDepartmentId());
        headquarter.setServiceBookNo(dto.getServiceBookNo());
        headquarter.setPost(dto.getPost());
        headquarter.setAppointmentFrom(dto.getAppointmentFrom());
        headquarter.setAppointmentTo(dto.getAppointmentTo());
        headquarter.setStatus(dto.getStatus());
    }

    private HeadquarterDto toDto(Headquarter headquarter) {
        return new HeadquarterDto(headquarter.getId(), headquarter.getName(), headquarter.getDesignationId(),
                headquarter.getMobile(), headquarter.getEmail(), headquarter.getDepartmentId(),
                headquarter.getServiceBookNo(), headquarter.getPost(), headquarter.getAppointmentFrom(),
                headquarter.getAppointmentTo(), headquarter.getStatus());
    }
}
