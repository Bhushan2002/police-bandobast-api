package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.HeadquarterDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.Headquarter;
import com.app.policebandobastapi.repository.HeadquarterRepo;
import com.app.policebandobastapi.service.HeadquarterService;

@Service
public class HeadquarterServiceImpl implements HeadquarterService {

    @Autowired
    private HeadquarterRepo repo;

    @Override
    public HeadquarterDto addHeadquarter(HeadquarterDto headquarterDto) {
        validate(headquarterDto);

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
    public HeadquarterDto findById(int id) {
        return toDto(getExisting(id));
    }

    @Override
    public HeadquarterDto updateHeadquarter(int id, HeadquarterDto headquarterDto) {
        Headquarter existing = getExisting(id);
        validate(headquarterDto);

        RecordStatus currentStatus = existing.getStatus();
        copyToEntity(headquarterDto, existing);
        if (headquarterDto.getStatus() == null) {
            existing.setStatus(currentStatus);
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteHeadquarter(int id) {
        Headquarter existing = getExisting(id);
        repo.delete(existing);
    }

    private Headquarter getExisting(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Headquarter", id));
    }

    private void validate(HeadquarterDto dto) {
        if (dto == null || dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("Headquarter name is required");
        }
        if (dto.getAppointmentFrom() != null && dto.getAppointmentTo() != null
                && dto.getAppointmentTo().isBefore(dto.getAppointmentFrom())) {
            throw new BadRequestException("Appointment to date cannot be before appointment from date");
        }
    }

    private void copyToEntity(HeadquarterDto dto, Headquarter headquarter) {
        headquarter.setName(dto.getName().trim());
        headquarter.setDesignationId(dto.getDesignationId());
        headquarter.setMobile(dto.getMobile());
        headquarter.setEmail(dto.getEmail());
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
