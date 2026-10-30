package com.app.policebandobastapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.model.RejectionTemplate;
import com.app.policebandobastapi.repository.RejectionTemplateRepo;

@Service
public class RejectionTemplateService {

    @Autowired
    private RejectionTemplateRepo repo;

    public RejectionTemplate save(RejectionTemplate rejectionTemplate) {
        return repo.save(rejectionTemplate);
    }

    public List<RejectionTemplate> getAll() {
        return repo.findAll();
    }

    public RejectionTemplate findById(int id) {
        return repo.findById(id).get();
    }

    public RejectionTemplate update(RejectionTemplate rejectionTemplate, int id) {
        RejectionTemplate existing = repo.findById(id).get();
        existing.setTitle(rejectionTemplate.getTitle());
        existing.setType(rejectionTemplate.getType());
        existing.setPoliceStationId(rejectionTemplate.getPoliceStationId());
        existing.setDescription(rejectionTemplate.getDescription());
        existing.setStatus(rejectionTemplate.getStatus());
        return repo.save(existing);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

}
