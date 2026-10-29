package com.app.policebandobastapi.controller;


import com.app.policebandobastapi.model.Department;
import com.app.policebandobastapi.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentsController {
    @Autowired
    private DepartmentService service;


    @GetMapping
    public List<Department> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> findById(@PathVariable int id) {
        Optional<Department> department = service.findById(id);
        if (department.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(department.get());
    }

    @PostMapping
    public ResponseEntity<Department> save(@RequestBody Department department) {
        try {
            Department saved = service.save(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable int id, @RequestBody Department department) {
        try {
            Optional<Department> updated = service.update(id, department);
            if (updated.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated.get());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        boolean deleted = service.delete(id);
        try {

            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
