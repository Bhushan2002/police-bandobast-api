package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.PoliceStationDto;

public interface PoliceStationService {

    public PoliceStationDto addPoliceStation(PoliceStationDto policeStation);

    public List<PoliceStationDto> getAllPoliceStation();

    public PoliceStationDto findById(Long id);

    public PoliceStationDto updatePoliceStation(Long id, PoliceStationDto policeStation);

    public void deletePoliceStation(Long id);

}
