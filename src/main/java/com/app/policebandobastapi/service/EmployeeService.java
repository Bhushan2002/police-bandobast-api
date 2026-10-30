package com.app.policebandobastapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.model.Employees;
import com.app.policebandobastapi.repository.EmployeesRepo;

@Service 
public class EmployeeService {
    
    @Autowired 
    private EmployeesRepo repo;


    public Employees save(Employees emp){
        return repo.save(emp);
    }



    public List<Employees> getAll(){
        return repo.findAll();
    }


    public Employees findById(int id){
        return repo.findById(id).get();
    }

    public Optional<Employees> update(int id, Employees emp){
        return repo.findById(id).map(existing -> {
            existing.setName(emp.getName());
           existing.setDesignationId(emp.getDesignationId());
            existing.setBuckleNo(emp.getBuckleNo());
            existing.setPoliceStationId(emp.getPoliceStationId());
            existing.setDepartmentId(emp.getDepartmentId());
            existing.setForceCategory(emp.getForceCategory());
            existing.setStatus(emp.getStatus());
        
            return repo.save(existing);
        });
    }


    public void delete(int id){
        repo.deleteById(id);
    }
}
