package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.DesignationDto;

public interface DesignationService {

    public DesignationDto addDesignation(DesignationDto designation);

    public List<DesignationDto> getAllDesignation();

    public DesignationDto findById(int id);

    public DesignationDto updateDesignation(int id, DesignationDto designation);

    public void deleteDesignation(int id);

}
