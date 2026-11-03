package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.RejectionTemplateDto;

public interface RejectionTemplateService {

    public RejectionTemplateDto addRejectionTemplate(RejectionTemplateDto rejectionTemplate);

    public List<RejectionTemplateDto> getAllRejectionTemplate();

    public RejectionTemplateDto findById(Long id);

    public RejectionTemplateDto updateRejectionTemplate(Long id, RejectionTemplateDto rejectionTemplate);

    public void deleteRejectionTemplate(Long id);

}
