package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.PoliceStationDto;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.DuplicateResourceException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.PoliceStation;
import com.app.policebandobastapi.repository.HeadquarterRepo;
import com.app.policebandobastapi.repository.PoliceStationRepo;
import com.app.policebandobastapi.service.PoliceStationService;
import com.app.policebandobastapi.util.ValidationUtils;

@Service
public class PoliceStationServiceImpl implements PoliceStationService {

    @Autowired
    private PoliceStationRepo repo;

    @Autowired
    private HeadquarterRepo headquarterRepo;

    @Override
    public PoliceStationDto addPoliceStation(PoliceStationDto policeStationDto) {
        validate(policeStationDto);
        checkDuplicates(policeStationDto, null);

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
    public PoliceStationDto findById(Long id) {
        return toDto(getExisting(id));
    }

    @Override
    public PoliceStationDto updatePoliceStation(Long id, PoliceStationDto policeStationDto) {
        PoliceStation existing = getExisting(id);
        validate(policeStationDto);
        checkDuplicates(policeStationDto, id);

        RecordStatus currentStatus = existing.getStatus();
        copyToEntity(policeStationDto, existing);
        if (policeStationDto.getStatus() == null) {
            existing.setStatus(currentStatus);
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deletePoliceStation(Long id) {
        PoliceStation existing = getExisting(id);
        repo.delete(existing);
    }

    private PoliceStation getExisting(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Police station", id));
    }

    private void validate(PoliceStationDto dto) {
        if (dto == null) {
            throw new BadRequestException("Police station details are required");
        }
        ValidationUtils.requireText(dto.getName(), "Police station name", 200);
        ValidationUtils.requireExists(headquarterRepo, dto.getHeadquarterId(), "Headquarter");
        ValidationUtils.requireText(dto.getDistrict(), "District", 100);
        ValidationUtils.optionalText(dto.getTaluka(), "Taluka", 100);
        ValidationUtils.optionalText(dto.getVillage(), "Village", 100);
        ValidationUtils.optionalText(dto.getCity(), "City", 100);
        ValidationUtils.optionalPincode(dto.getPincode());
        ValidationUtils.optionalText(dto.getLandline(), "Landline number", 20);
        ValidationUtils.requireMobile(dto.getMobile(), "Mobile number");
    }

    // id = null on create, so the IdNot check is only used on update
    private void checkDuplicates(PoliceStationDto dto, Long id) {
        boolean mobileTaken = id == null ? repo.existsByMobile(dto.getMobile())
                : repo.existsByMobileAndIdNot(dto.getMobile(), id);
        if (mobileTaken) {
            throw new DuplicateResourceException("Police station already exists with mobile number " + dto.getMobile());
        }
    }

    private void copyToEntity(PoliceStationDto dto, PoliceStation policeStation) {
        policeStation.setName(dto.getName().trim());
        policeStation.setHeadquarterId(dto.getHeadquarterId());
        policeStation.setDistrict(dto.getDistrict().trim());
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
