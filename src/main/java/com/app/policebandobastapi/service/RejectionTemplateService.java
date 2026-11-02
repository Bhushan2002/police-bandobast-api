package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.RejectionTemplateDto;

public interface RejectionTemplateService {

    public RejectionTemplateDto addRejectionTemplate(RejectionTemplateDto rejectionTemplate);

    public List<RejectionTemplateDto> getAllRejectionTemplate();

    public RejectionTemplateDto findById(int id);

    public RejectionTemplateDto updateRejectionTemplate(int id, RejectionTemplateDto rejectionTemplate);

    public void deleteRejectionTemplate(int id);

}
