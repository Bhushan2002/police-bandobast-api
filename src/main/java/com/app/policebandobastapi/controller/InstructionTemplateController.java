package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.InstructionTemplateDto;
import com.app.policebandobastapi.service.InstructionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/instruction-template")
public class InstructionTemplateController {
    @Autowired
    private InstructionTemplateService service;

    @GetMapping
    public List<InstructionTemplateDto> findAll() {
        return service.getAllInstructionTemplate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructionTemplateDto> findById(@PathVariable int id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InstructionTemplateDto> save(@RequestBody InstructionTemplateDto instructionTemplate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addInstructionTemplate(instructionTemplate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructionTemplateDto> update(@PathVariable int id, @RequestBody InstructionTemplateDto instructionTemplate) {
        return ResponseEntity.ok(service.updateInstructionTemplate(id, instructionTemplate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteInstructionTemplate(id);
        return ResponseEntity.noContent().build();
    }
}
