
package com.app.policebandobastapi.serviceImpl;

import com.app.policebandobastapi.dto.ForceRequestDto;
import com.app.policebandobastapi.enums.Enums.ForceResponseStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.Bandobast;
import com.app.policebandobastapi.model.ForceRequest;
import com.app.policebandobastapi.model.PoliceStation;
import com.app.policebandobastapi.repository.BandobastRepository;
import com.app.policebandobastapi.repository.ForceRequestRepository;
import com.app.policebandobastapi.repository.PoliceStationRepo;
import com.app.policebandobastapi.service.ForceRequestService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ForceRequestServiceImpl implements ForceRequestService {

    private final ForceRequestRepository forceRequestRepository;
    private final BandobastRepository bandobastRepository;
    private final PoliceStationRepo policeStationRepository;

    public ForceRequestServiceImpl(
            ForceRequestRepository forceRequestRepository,
            BandobastRepository bandobastRepository,
            PoliceStationRepo policeStationRepository) {

        this.forceRequestRepository = forceRequestRepository;
        this.bandobastRepository = bandobastRepository;
        this.policeStationRepository = policeStationRepository;
    }

    // Create Force Request
    @Override
    public ForceRequest createForceRequest(
            Long bandobastId,
            ForceRequestDto dto) {

        validateForceRequest(dto);

        Bandobast bandobast = getBandobast(bandobastId);

        PoliceStation policeStation =
                getPoliceStation(dto.getPoliceStationId());

        // Check duplicate request
        boolean alreadyExists =
                forceRequestRepository.existsByBandobastIdAndPoliceStationId(
                        bandobastId,
                        dto.getPoliceStationId()
                );

        if (alreadyExists) {
            throw new BadRequestException(
                    "This police station has already submitted a force request for this Bandobast"
            );
        }

        ForceRequest forceRequest = new ForceRequest();

        forceRequest.setBandobast(bandobast);
        forceRequest.setPoliceStation(policeStation);

        setForceDetails(forceRequest, dto);

        forceRequest.setStatus(ForceResponseStatus.PENDING);

        return forceRequestRepository.save(forceRequest);
    }

    // Get All Force Requests for a Bandobast
    @Override
    @Transactional(readOnly = true)
    public List<ForceRequest> getAllForceRequests(Long bandobastId) {

        getBandobast(bandobastId);

        return forceRequestRepository.findByBandobastId(bandobastId);
    }

    // Find Force Request by ID
    @Override
    @Transactional(readOnly = true)
    public ForceRequest findById(Long bandobastId, Long requestId) {

        getBandobast(bandobastId);

        return getForceRequest(bandobastId, requestId);
    }

    // Update Force Request
    @Override
    public ForceRequest updateForceRequest(
            Long bandobastId,
            Long requestId,
            ForceRequestDto dto) {

        validateForceRequest(dto);

        ForceRequest forceRequest =
                getForceRequest(bandobastId, requestId);

        PoliceStation policeStation =
                getPoliceStation(dto.getPoliceStationId());

        // Check duplicate only if the station is being changed
        if (!forceRequest.getPoliceStation().getId()
                .equals(dto.getPoliceStationId())) {

            boolean alreadyExists =
                    forceRequestRepository.existsByBandobastIdAndPoliceStationId(
                            bandobastId,
                            dto.getPoliceStationId()
                    );

            if (alreadyExists) {
                throw new BadRequestException(
                        "This police station has already submitted a force request for this Bandobast"
                );
            }
        }

        forceRequest.setPoliceStation(policeStation);

        setForceDetails(forceRequest, dto);

        return forceRequestRepository.save(forceRequest);
    }

    // Delete Force Request
    @Override
    public void deleteForceRequest(Long bandobastId, Long requestId) {

        ForceRequest forceRequest =
                getForceRequest(bandobastId, requestId);

        forceRequestRepository.delete(forceRequest);
    }

    // Get Bandobast
    private Bandobast getBandobast(Long bandobastId) {

        return bandobastRepository.findById(bandobastId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bandobast", bandobastId));
    }

    // Get Police Station
    private PoliceStation getPoliceStation(Long policeStationId) {

        if (policeStationId == null) {
            throw new BadRequestException("Police Station ID is required");
        }

        return policeStationRepository.findById(policeStationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Police Station", policeStationId));
    }

    // Get Force Request
    private ForceRequest getForceRequest(
            Long bandobastId,
            Long requestId) {

        ForceRequest forceRequest =
                forceRequestRepository.findById(requestId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Force Request", requestId));

        if (!forceRequest.getBandobast().getId().equals(bandobastId)) {
            throw new ResourceNotFoundException(
                    "Force Request", requestId);
        }

        return forceRequest;
    }

    // Validate Force Request
    private void validateForceRequest(ForceRequestDto dto) {

        if (dto == null) {
            throw new BadRequestException("Force Request data is required");
        }

        if (dto.getPoliceStationId() == null) {
            throw new BadRequestException("Police Station ID is required");
        }

        if (dto.getRequestDate() == null) {
            throw new BadRequestException("Request date is required");
        }

        validateCount(dto.getSrOfficer(), "Senior Officer");
        validateCount(dto.getDcp(), "DCP");
        validateCount(dto.getSrPi(), "Senior PI");
        validateCount(dto.getPi(), "PI");
        validateCount(dto.getApi(), "API");
        validateCount(dto.getHc(), "Police Hawaldar");
        validateCount(dto.getPcMale(), "Male Constable");
        validateCount(dto.getPcFemale(), "Female Constable");
        validateCount(dto.getTraffic(), "Traffic");
        validateCount(dto.getGeneralForce(), "General Force");
        validateCount(dto.getHomeGuard(), "Home Guard");
        validateCount(dto.getWarden(), "Warden");
    }

    // Validate individual force count
    private void validateCount(Integer count, String fieldName) {

        if (count != null && count < 0) {
            throw new BadRequestException(
                    fieldName + " count cannot be negative");
        }
    }

    // Set Force Details and Calculate Total
    private void setForceDetails(
            ForceRequest forceRequest,
            ForceRequestDto dto) {

        forceRequest.setRequestDate(dto.getRequestDate());

        forceRequest.setSrOfficer(getCount(dto.getSrOfficer()));
        forceRequest.setDcp(getCount(dto.getDcp()));
        forceRequest.setSrPi(getCount(dto.getSrPi()));
        forceRequest.setPi(getCount(dto.getPi()));
        forceRequest.setApi(getCount(dto.getApi()));
        forceRequest.setHc(getCount(dto.getHc()));
        forceRequest.setPcMale(getCount(dto.getPcMale()));
        forceRequest.setPcFemale(getCount(dto.getPcFemale()));
        forceRequest.setTraffic(getCount(dto.getTraffic()));
        forceRequest.setGeneralForce(getCount(dto.getGeneralForce()));
        forceRequest.setHomeGuard(getCount(dto.getHomeGuard()));
        forceRequest.setWarden(getCount(dto.getWarden()));

        int total =
                forceRequest.getSrOfficer()
                        + forceRequest.getDcp()
                        + forceRequest.getSrPi()
                        + forceRequest.getPi()
                        + forceRequest.getApi()
                        + forceRequest.getHc()
                        + forceRequest.getPcMale()
                        + forceRequest.getPcFemale()
                        + forceRequest.getTraffic()
                        + forceRequest.getGeneralForce()
                        + forceRequest.getHomeGuard()
                        + forceRequest.getWarden();

        forceRequest.setTotal(total);
    }

    // Convert null count to zero
    private Integer getCount(Integer count) {
        return count == null ? 0 : count;
    }
}
