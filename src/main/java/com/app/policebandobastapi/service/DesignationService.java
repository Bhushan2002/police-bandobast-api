package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.DesignationDto;

public interface DesignationService {

    public DesignationDto addDesignation(DesignationDto designation);

    public List<DesignationDto> getAllDesignation();

    public DesignationDto findById(Long id);

    public DesignationDto updateDesignation(Long id, DesignationDto designation);

    public void deleteDesignation(Long id);

}
