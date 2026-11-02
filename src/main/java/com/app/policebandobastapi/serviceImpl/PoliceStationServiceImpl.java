package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.PoliceStationDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.PoliceStation;
import com.app.policebandobastapi.repository.PoliceStationRepo;
import com.app.policebandobastapi.service.PoliceStationService;

@Service
public class PoliceStationServiceImpl implements PoliceStationService {

    @Autowired
    private PoliceStationRepo repo;

    @Override
    public PoliceStationDto addPoliceStation(PoliceStationDto policeStationDto) {
        validate(policeStationDto);

        PoliceStation policeStation = new PoliceStation();
        copyToEntity(policeStationDto, policeStation);
        if (policeStation.getStatus() == null) {
            policeStation.setStatus(RecordStatus.ACTIVE);
        }
        return toDto(repo.save(policeStation));
    }

    @Override
    public List<PoliceStationDto> getAllPoliceStation() {
        List<PoliceStation> policeStations = repo.findAll();

        return policeStations.stream()
                .map(policeStation -> toDto(policeStation))
                .toList();
    }

    @Override
    public PoliceStationDto findById(int id) {
        return toDto(getExisting(id));
    }

    @Override
    public PoliceStationDto updatePoliceStation(int id, PoliceStationDto policeStationDto) {
        PoliceStation existing = getExisting(id);
        validate(policeStationDto);

        RecordStatus currentStatus = existing.getStatus();
        copyToEntity(policeStationDto, existing);
        if (policeStationDto.getStatus() == null) {
            existing.setStatus(currentStatus);
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deletePoliceStation(int id) {
        PoliceStation existing = getExisting(id);
        repo.delete(existing);
    }

    private PoliceStation getExisting(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Police station", id));
    }

    private void validate(PoliceStationDto dto) {
        if (dto == null || dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("Police station name is required");
        }
    }

    private void copyToEntity(PoliceStationDto dto, PoliceStation policeStation) {
        policeStation.setName(dto.getName().trim());
        policeStation.setHeadquarterId(dto.getHeadquarterId());
        policeStation.setDistrict(dto.getDistrict());
        policeStation.setTaluka(dto.getTaluka());
        policeStation.setVillage(dto.getVillage());
        policeStation.setCity(dto.getCity());
        policeStation.setMobile(dto.getMobile());
        policeStation.setPincode(dto.getPincode());
        policeStation.setLandline(dto.getLandline());
        policeStation.setStatus(dto.getStatus());
    }

    private PoliceStationDto toDto(PoliceStation policeStation) {
        return new PoliceStationDto(policeStation.getId(), policeStation.getName(),
                policeStation.getHeadquarterId(), policeStation.getDistrict(), policeStation.getTaluka(),
                policeStation.getVillage(), policeStation.getCity(), policeStation.getMobile(),
                policeStation.getPincode(), policeStation.getLandline(), policeStation.getStatus());
    }
}
