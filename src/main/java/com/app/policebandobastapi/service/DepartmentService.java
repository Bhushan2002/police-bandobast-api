package com.app.policebandobastapi.service;


import com.app.policebandobastapi.enums.Enums;
import com.app.policebandobastapi.model.Department;
import com.app.policebandobastapi.repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo repo;

    public List<Department> findAll() {
        return repo.findAll();
    }

    public Optional<Department> findById(int id) {
        return repo.findById(id);
    }

    public Department save(Department department) {
        if (department.getStatus() == null) {
            department.setStatus(Enums.Status.ACTIVE);
        }
        return repo.save(department);
    }

    public Optional<Department> update(int id, Department department) {
        return repo.findById(id).map(existing -> {
            existing.setName(department.getName());
            if (department.getStatus() != null) {
                existing.setStatus(department.getStatus());
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
