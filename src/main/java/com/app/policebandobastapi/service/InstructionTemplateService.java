package com.app.policebandobastapi.service;

import java.util.List;

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


    public List<InstructionTemplate> getAll(){
        return repo.findAll();
    }

    public InstructionTemplate findById(int id){
        return repo.findById(id).get();
    }

    public InstructionTemplate update(InstructionTemplate instructionTemplate, int id){
        return repo.findById(id).map(existing -> {
            existing.setTitle(instructionTemplate.getTitle());
            existing.setDescription(instructionTemplate.getDescription());
            existing.setStatus(instructionTemplate.getStatus());
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Instruction Template not found with id " + id));
    }

    public void delete(int id){
        repo.deleteById(id);
    }



    
}
