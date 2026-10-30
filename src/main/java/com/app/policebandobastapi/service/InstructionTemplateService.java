package com.app.policebandobastapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.model.InstructionTemplate;
import com.app.policebandobastapi.repository.InstructionTemplateRepo;

@Service 
public class InstructionTemplateService {
    
    @Autowired 
    private InstructionTemplateRepo repo;



    public InstructionTemplate save (InstructionTemplate instructionTemplate){
        return repo.save(instructionTemplate);

    }
    

    
}
