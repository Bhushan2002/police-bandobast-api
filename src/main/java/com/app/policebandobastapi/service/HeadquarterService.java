package com.app.policebandobastapi.service;

import java.util.List;

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
    public Headquarter update(Headquarter hq){
        return repo.save(hq);
    }

    // delete
    public void delete(int id){
        repo.deleteById(id);
    }

}
