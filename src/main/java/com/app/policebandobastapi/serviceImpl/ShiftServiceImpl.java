package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.ShiftDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.DuplicateResourceException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.Shift;
import com.app.policebandobastapi.repository.ShiftRepo;
import com.app.policebandobastapi.service.ShiftService;

@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftRepo repo;

    @Override
    public ShiftDto addShift(ShiftDto shiftDto) {
        validate(shiftDto);
        String name = shiftDto.getName().trim();
        if (repo.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Shift already exists with name " + name);
        }

        Shift shift = new Shift();
        copyToEntity(shiftDto, shift);
        if (shift.getStatus() == null) {
            shift.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(shift));
    }

    @Override
    public List<ShiftDto> getAllShift() {
        List<Shift> shifts = repo.findAll();

        return shifts.stream()
                .map(shift -> toDto(shift))
                .toList();
    }

    @Override
    public ShiftDto findById(int id) {
        return toDto(getExisting(id));
    }

    @Override
    public ShiftDto updateShift(int id, ShiftDto shiftDto) {
        Shift existing = getExisting(id);
        validate(shiftDto);
        String name = shiftDto.getName().trim();
        if (repo.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new DuplicateResourceException("Shift already exists with name " + name);
        }

        RecordStatus currentStatus = existing.getStatus();
        copyToEntity(shiftDto, existing);
        if (shiftDto.getStatus() == null) {
            existing.setStatus(currentStatus);
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteShift(int id) {
        Shift existing = getExisting(id);
        repo.delete(existing);
    }

    private Shift getExisting(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shift", id));
    }

    private void validate(ShiftDto dto) {
        if (dto == null || dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("Shift name is required");
        }
        if (dto.getStartTime() == null || dto.getEndTime() == null) {
            throw new BadRequestException("Shift start time and end time are required");
        }
        if (dto.getShiftType() == null) {
            throw new BadRequestException("Shift type is required");
        }
    }

    private void copyToEntity(ShiftDto dto, Shift shift) {
        shift.setName(dto.getName().trim());
        shift.setStartTime(dto.getStartTime());
        shift.setEndTime(dto.getEndTime());
        shift.setShiftType(dto.getShiftType());
        shift.setDayOffAfter24hr(dto.isDayOffAfter24hr());
        shift.setStatus(dto.getStatus());
    }

    private ShiftDto toDto(Shift shift) {
        return new ShiftDto(shift.getId(), shift.getName(), shift.getStartTime(), shift.getEndTime(),
                shift.getShiftType(), shift.isDayOffAfter24hr(), shift.getStatus());
    }
}
