package com.app.policebandobastapi.service;


import com.app.policebandobastapi.enums.Enums;
import com.app.policebandobastapi.model.Designation;
import com.app.policebandobastapi.repository.DesignationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignationService {
    @Autowired
    private DesignationRepo repo;

    public List<Designation> findAll() {
        return repo.findAll();
    }

    public Optional<Designation> findById(int id) {
        return repo.findById(id);
    }

    public Designation save(Designation designation) {
        if (designation.getStatus() == null) {
            designation.setStatus(Enums.Status.ACTIVE);
        }
        return repo.save(designation);
    }

    public Optional<Designation> update(int id, Designation designation) {
        return repo.findById(id).map(existing -> {
            existing.setName(designation.getName());
            if (designation.getStatus() != null) {
                existing.setStatus(designation.getStatus());
            }
            return repo.save(existing);
        });
    }

    // Soft delete: keeps the row so records that reference it stay valid
    public boolean delete(int id) {
        return repo.findById(id).map(existing -> {
            existing.setStatus(Enums.Status.INACTIVE);
            repo.save(existing);
            return true;
        }).orElse(false);
    }
}
