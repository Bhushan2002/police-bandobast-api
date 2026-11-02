package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.EmployeeDto;
import com.app.policebandobastapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeesController {
    @Autowired
    private EmployeeService service;

    @GetMapping
    public List<EmployeeDto> findAll() {
        return service.getAllEmployee();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable int id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable int id, @RequestBody EmployeeDto employee) {
        return ResponseEntity.ok(service.updateEmployee(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
