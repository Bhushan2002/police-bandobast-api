
package com.app.policebandobastapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.policebandobastapi.model.RejectionTemplate;
import com.app.policebandobastapi.service.RejectionTemplateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/rejection-template")
public class RejectionTemplateController {

    @Autowired
    private RejectionTemplateService service;

    @PostMapping
    public ResponseEntity<RejectionTemplate> save(@RequestBody RejectionTemplate rejectionTemplate) {
        try {
            if (rejectionTemplate == null) {
                return ResponseEntity.badRequest().build();
            } else {
                RejectionTemplate savedTemplate = service.save(rejectionTemplate);
                return ResponseEntity.ok(savedTemplate);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RejectionTemplate> getById(@PathVariable int id) {

        try {
            RejectionTemplate template = service.findById(id);
            if (template == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(template);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<RejectionTemplate>> getall() {
        try {
            if (service.getAll().isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(service.getAll());
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RejectionTemplate> update(@PathVariable int id,
            @RequestBody RejectionTemplate rejectionTemplate) {

        try {
            RejectionTemplate updatedTemplate = service.update(rejectionTemplate, id);
            if (updatedTemplate == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(updatedTemplate);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.delete(id);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}