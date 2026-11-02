package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.PoliceStationDto;

public interface PoliceStationService {

    public PoliceStationDto addPoliceStation(PoliceStationDto policeStation);

    public List<PoliceStationDto> getAllPoliceStation();

    public PoliceStationDto findById(int id);

    public PoliceStationDto updatePoliceStation(int id, PoliceStationDto policeStation);

    public void deletePoliceStation(int id);

}
