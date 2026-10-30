package com.app.policebandobastapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.model.Headquarter;
import com.app.policebandobastapi.repository.HeadquarterRepo;

@Service 
public class HeadquarterService {


    @Autowired 
    private HeadquarterRepo repo;



    // post
    public Headquarter save(Headquarter hq){
        return repo.save(hq);
    } 

    //getall
    public List<Headquarter> getAll(){
        return repo.findAll();
    }

    // getbyid
    public Headquarter getById(int id){
        return repo.findById(id).get();
    }

    //update
    public Optional<Headquarter> update(Headquarter hq,int id ){
        return repo.findById(id).map(existing -> {
            existing.setName(hq.getName());
            existing.setDesignationId(hq.getDesignationId());
            existing.setMobile(hq.getMobile());
            existing.setEmail(hq.getEmail());
            existing.setDepartmentId(hq.getDepartmentId());
            existing.setServiceBookNo(hq.getServiceBookNo());
            existing.setPost(hq.getPost());
            existing.setAppointmentFrom(hq.getAppointmentFrom());
            existing.setAppointmentTo(hq.getAppointmentTo());
            if (hq.getStatus() != null) {
                existing.setStatus(hq.getStatus());
            }
            return repo.save(existing);
        });
        
    }

    // delete
    public void delete(int id){
        repo.deleteById(id);
    }

}
