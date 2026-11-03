package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.SeniorOfficerDto;

public interface SeniorOfficerService {

    public SeniorOfficerDto addSeniorOfficer(SeniorOfficerDto seniorOfficer);

    public List<SeniorOfficerDto> getAllSeniorOfficer();

    public SeniorOfficerDto findById(Long id);

    public SeniorOfficerDto updateSeniorOfficer(Long id, SeniorOfficerDto seniorOfficer);

    public void deleteSeniorOfficer(Long id);

}
