package com.app.policebandobastapi.service;


import com.app.policebandobastapi.model.SeniorOfficer;
import com.app.policebandobastapi.repository.SeniorOfficerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeniorOfficerService {

    @Autowired
    private SeniorOfficerRepo repo;


    public SeniorOfficer save(SeniorOfficer so) {
        return repo.save(so);
    }

    public SeniorOfficer findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public List<SeniorOfficer> findAll() {
        return repo.findAll();
    }

    public Optional<SeniorOfficer> update(int id, SeniorOfficer so) {
        return repo.findById(id).map(existing -> {
            existing.setName(so.getName());
            existing.setDesignationId(so.getDesignationId());
            existing.setMobile(so.getMobile());
            existing.setEmial(so.getEmial());
            existing.setDepartmentId(so.getDepartmentId());
            existing.setServiceBookNo(so.getServiceBookNo());
            existing.setPost(so.getPost());
            existing.setAppointmentDateFrom(so.getAppointmentDateFrom());
            existing.setAppointmentDateTo(so.getAppointmentDateTo());
            if (so.getStatus() != null) {
                existing.setStatus(so.getStatus());
            }
            return repo.save(existing);
        });
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
