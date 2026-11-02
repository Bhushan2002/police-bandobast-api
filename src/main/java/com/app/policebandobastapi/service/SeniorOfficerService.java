package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.SeniorOfficerDto;

public interface SeniorOfficerService {

    public SeniorOfficerDto addSeniorOfficer(SeniorOfficerDto seniorOfficer);

    public List<SeniorOfficerDto> getAllSeniorOfficer();

    public SeniorOfficerDto findById(int id);

    public SeniorOfficerDto updateSeniorOfficer(int id, SeniorOfficerDto seniorOfficer);

    public void deleteSeniorOfficer(int id);

}
