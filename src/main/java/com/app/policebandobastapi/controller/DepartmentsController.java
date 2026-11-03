package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.DepartmentDto;
import com.app.policebandobastapi.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/department")
public class DepartmentsController {
    @Autowired
    private DepartmentService service;

    @GetMapping
    public List<DepartmentDto> findAll() {
        return service.getAllDepartment();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> save(@RequestBody DepartmentDto department) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addDepartment(department));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> update(@PathVariable Long id, @RequestBody DepartmentDto department) {
        return ResponseEntity.ok(service.updateDepartment(id, department));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
