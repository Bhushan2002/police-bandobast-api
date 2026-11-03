
package com.app.policebandobastapi.serviceImpl;

import com.app.policebandobastapi.dto.DutyPointDto;
import com.app.policebandobastapi.dto.DutyPointResponseDto;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.Bandobast;
import com.app.policebandobastapi.model.DutyPoint;
import com.app.policebandobastapi.repository.BandobastRepository;
import com.app.policebandobastapi.repository.DutyPointRepository;
import com.app.policebandobastapi.service.DutyPointService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DutyPointServiceImpl implements DutyPointService {

    private final DutyPointRepository dutyPointRepository;
    private final BandobastRepository bandobastRepository;

    // Constructor injection
    public DutyPointServiceImpl(
            DutyPointRepository dutyPointRepository,
            BandobastRepository bandobastRepository) {

        this.dutyPointRepository = dutyPointRepository;
        this.bandobastRepository = bandobastRepository;
    }

    // Add new Duty Point
    @Override
    public DutyPointResponseDto addDutyPoint(
            Long bandobastId,
            DutyPointDto dutyPointDto) {

        Bandobast bandobast = getBandobast(bandobastId);

        validateDutyPoint(dutyPointDto);

        DutyPoint dutyPoint = new DutyPoint();

        dutyPoint.setBandobast(bandobast);
        dutyPoint.setName(dutyPointDto.getCheckpointName());
        dutyPoint.setLatitude(dutyPointDto.getLatitude());
        dutyPoint.setLongitude(dutyPointDto.getLongitude());

        dutyPoint.setRadiusMeters(
                dutyPointDto.getRadiusMeters() != null
                        ? dutyPointDto.getRadiusMeters()
                        : 200
        );

        dutyPoint.setNote(dutyPointDto.getNote());

        DutyPoint savedPoint = dutyPointRepository.save(dutyPoint);

        return convertToResponseDto(savedPoint);
    }

    // Get all Duty Points of a Bandobast
    @Override
    @Transactional(readOnly = true)
    public List<DutyPointResponseDto> getAllDutyPoints(Long bandobastId) {

        getBandobast(bandobastId);

        List<DutyPoint> dutyPoints =
                dutyPointRepository.findByBandobastId(bandobastId);

        return dutyPoints.stream()
                .map(this::convertToResponseDto)
                .toList();
    }

    // Get Duty Point by ID
    @Override
    @Transactional(readOnly = true)
    public DutyPointResponseDto findById(
            Long bandobastId,
            Long pointId) {

        DutyPoint dutyPoint = getDutyPoint(bandobastId, pointId);

        return convertToResponseDto(dutyPoint);
    }

    // Update existing Duty Point
    @Override
    public DutyPointResponseDto updateDutyPoint(
            Long bandobastId,
            Long pointId,
            DutyPointDto dutyPointDto) {

        DutyPoint dutyPoint = getDutyPoint(bandobastId, pointId);

        validateDutyPoint(dutyPointDto);

        dutyPoint.setName(dutyPointDto.getCheckpointName());
        dutyPoint.setLatitude(dutyPointDto.getLatitude());
        dutyPoint.setLongitude(dutyPointDto.getLongitude());

        dutyPoint.setRadiusMeters(
                dutyPointDto.getRadiusMeters() != null
                        ? dutyPointDto.getRadiusMeters()
                        : 200
        );

        dutyPoint.setNote(dutyPointDto.getNote());

        DutyPoint updatedPoint = dutyPointRepository.save(dutyPoint);

        return convertToResponseDto(updatedPoint);
    }

    // Delete Duty Point
    @Override
    public void deleteDutyPoint(Long bandobastId, Long pointId) {

        DutyPoint dutyPoint = getDutyPoint(bandobastId, pointId);

        dutyPointRepository.delete(dutyPoint);
    }

    // Find Bandobast by ID
    private Bandobast getBandobast(Long bandobastId) {

        return bandobastRepository.findById(bandobastId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bandobast", bandobastId));
    }

    // Find Duty Point and verify its Bandobast
    private DutyPoint getDutyPoint(Long bandobastId, Long pointId) {

        return dutyPointRepository.findById(pointId)
                .filter(point ->
                        point.getBandobast().getId().equals(bandobastId))
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Duty Point", pointId));
    }

    // Validate Duty Point details
    private void validateDutyPoint(DutyPointDto dto) {

        if (dto == null) {
            throw new BadRequestException(
                    "Duty Point details are required");
        }

        if (dto.getCheckpointName() == null ||
                dto.getCheckpointName().trim().isEmpty()) {

            throw new BadRequestException(
                    "Checkpoint name is required");
        }

        if (dto.getCheckpointName().length() > 200) {
            throw new BadRequestException(
                    "Checkpoint name cannot exceed 200 characters");
        }

        if (dto.getLatitude() == null || dto.getLongitude() == null) {
            throw new BadRequestException(
                    "Latitude and longitude are required");
        }

        if (dto.getLatitude() < -90 || dto.getLatitude() > 90) {
            throw new BadRequestException("Invalid latitude");
        }

        if (dto.getLongitude() < -180 || dto.getLongitude() > 180) {
            throw new BadRequestException("Invalid longitude");
        }

        if (dto.getRadiusMeters() != null &&
                dto.getRadiusMeters() <= 0) {

            throw new BadRequestException(
                    "Radius must be greater than zero");
        }

        if (dto.getNote() != null && dto.getNote().length() > 500) {
            throw new BadRequestException(
                    "Note cannot exceed 500 characters");
        }
    }

    // Convert Entity to Response DTO
    private DutyPointResponseDto convertToResponseDto(
            DutyPoint dutyPoint) {

        return new DutyPointResponseDto(
                dutyPoint.getId(),
                dutyPoint.getBandobast().getId(),
                dutyPoint.getName(),
                dutyPoint.getLatitude(),
                dutyPoint.getLongitude(),
                dutyPoint.getRadiusMeters(),
                dutyPoint.getNote()
        );
    }
}
