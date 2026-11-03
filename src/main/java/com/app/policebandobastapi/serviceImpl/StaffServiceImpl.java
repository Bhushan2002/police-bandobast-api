package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.StaffDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.DuplicateResourceException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.Staff;
import com.app.policebandobastapi.repository.DepartmentRepo;
import com.app.policebandobastapi.repository.DesignationRepo;
import com.app.policebandobastapi.repository.PoliceStationRepo;
import com.app.policebandobastapi.repository.StaffRepo;
import com.app.policebandobastapi.service.StaffService;
import com.app.policebandobastapi.util.ValidationUtils;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepo repo;

    @Autowired
    private DesignationRepo designationRepo;

    @Autowired
    private PoliceStationRepo policeStationRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    private final BCryptPasswordEncoder pinEncoder = new BCryptPasswordEncoder();

    @Override
    public StaffDto addStaff(StaffDto staffDto) {
        validate(staffDto);
        if (staffDto.getAppPin() == null) {
            throw new BadRequestException("App pin is required");
        }
        checkDuplicates(staffDto, null);

        Staff staff = new Staff();
        copyToEntity(staffDto, staff);
        if (staff.getStatus() == null) {
            staff.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(staff));
    }

    @Override
    public List<StaffDto> getAllStaff() {
        List<Staff> staffList = repo.findAll();

        return staffList.stream()
                .map(staff -> toDto(staff))
                .toList();
    }

    @Override
    public StaffDto findById(Long id) {
        return toDto(getExisting(id));
    }

    @Override
    public StaffDto updateStaff(Long id, StaffDto staffDto) {
        Staff existing = getExisting(id);
        validate(staffDto);
        checkDuplicates(staffDto, id);

        RecordStatus currentStatus = existing.getStatus();
        copyToEntity(staffDto, existing);
        if (staffDto.getStatus() == null) {
            existing.setStatus(currentStatus);
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteStaff(Long id) {
        Staff existing = getExisting(id);
        repo.delete(existing);
    }

    private Staff getExisting(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", id));
    }

    private void validate(StaffDto dto) {
        if (dto == null) {
            throw new BadRequestException("Staff details are required");
        }
        ValidationUtils.requireText(dto.getName(), "Staff name", 150);
        ValidationUtils.requireExists(designationRepo, dto.getDesignationId(), "Designation");
        ValidationUtils.requireText(dto.getBuckleNo(), "Buckle number", 20);
        ValidationUtils.requireMobile(dto.getMobileNo(), "Mobile number");
        ValidationUtils.requireExists(policeStationRepo, dto.getPoliceStationId(), "Police station");
        ValidationUtils.optionalExists(departmentRepo, dto.getDepartmentId(), "Department");
        if (dto.getForceCategory() == null) {
            throw new BadRequestException("Force category is required");
        }
        if (dto.getAppPin() != null && !dto.getAppPin().matches("\\d{4}")) {
            throw new BadRequestException("App pin must be 4 digits");
        }
    }

    // id = null on create, so the IdNot checks are only used on update
    private void checkDuplicates(StaffDto dto, Long id) {
        String buckleNo = dto.getBuckleNo().trim();
        String mobileNo = dto.getMobileNo().trim();
        boolean buckleTaken = id == null ? repo.existsByBuckleNoIgnoreCase(buckleNo)
                : repo.existsByBuckleNoIgnoreCaseAndIdNot(buckleNo, id);
        if (buckleTaken) {
            throw new DuplicateResourceException("Staff already exists with buckle number " + buckleNo);
        }
        boolean mobileTaken = id == null ? repo.existsByMobileNo(mobileNo)
                : repo.existsByMobileNoAndIdNot(mobileNo, id);
        if (mobileTaken) {
            throw new DuplicateResourceException("Staff already exists with mobile number " + mobileNo);
        }
    }

    // The pin is only re-hashed when the request sends one; otherwise the existing hash is kept
    private void copyToEntity(StaffDto dto, Staff staff) {
        staff.setName(dto.getName().trim());
        staff.setDesignationId(dto.getDesignationId());
        staff.setBuckleNo(dto.getBuckleNo().trim());
        staff.setMobileNo(dto.getMobileNo().trim());
        staff.setPoliceStationId(dto.getPoliceStationId());
        staff.setDepartmentId(dto.getDepartmentId());
        staff.setForceCategory(dto.getForceCategory());
        if (dto.getAppPin() != null) {
            staff.setAppPinHash(pinEncoder.encode(dto.getAppPin()));
        }
        staff.setStatus(dto.getStatus());
    }

    private StaffDto toDto(Staff staff) {
        return new StaffDto(staff.getId(), staff.getName(), staff.getDesignationId(), staff.getBuckleNo(),
                staff.getMobileNo(), staff.getPoliceStationId(), staff.getDepartmentId(),
                staff.getForceCategory(), null, staff.getStatus());
    }
}
