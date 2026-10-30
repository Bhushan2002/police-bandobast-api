package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.repository.InstructionTemplateRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.policebandobastapi.model.InstructionTemplate;
import com.app.policebandobastapi.service.InstructionTemplateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/instruction-template")
public class InstructionTemplateController {    

    private final InstructionTemplateRepo instructionTemplateRepo;
    @Autowired
    private InstructionTemplateService service;

    InstructionTemplateController(InstructionTemplateRepo instructionTemplateRepo) {
        this.instructionTemplateRepo = instructionTemplateRepo;
    }

    @PostMapping
    public ResponseEntity<InstructionTemplate> save(@RequestBody InstructionTemplate instructionTemplate) {
        try {
            if (instructionTemplate == null) {
                return ResponseEntity.badRequest().build();
            }
            InstructionTemplate saved = service.save(instructionTemplate);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructionTemplate> getById(@PathVariable int id) {
        try {
            InstructionTemplate instructionTemplate = service.findById(id);
            if (instructionTemplate == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(instructionTemplate);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<InstructionTemplate>> getAll() {
        try {
            List<InstructionTemplate> instructionTemplates = service.getAll();
            if (instructionTemplates.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(instructionTemplates);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<InstructionTemplate> update(@PathVariable String id, @RequestBody InstructionTemplate instructionTemplate) {
        
        try {
            InstructionTemplate updated = service.update(instructionTemplate, Integer.parseInt(id));
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        try {
            InstructionTemplate existing = service.findById(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }
            service.delete(id);
            return ResponseEntity.ok("Instruction Template deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting Instruction Template: " + e.getMessage());
        }
    }
    

}
