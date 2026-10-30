package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.model.Headquarter;
import com.app.policebandobastapi.model.SeniorOfficer;
import com.app.policebandobastapi.repository.DepartmentRepo;
import com.app.policebandobastapi.service.SeniorOfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/senior-officer")
public class SeniorOfficerController {

    private final DepartmentRepo departmentRepo;
    @Autowired
    private SeniorOfficerService service;

    SeniorOfficerController(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    @PostMapping
    public ResponseEntity<SeniorOfficer> save(@RequestBody SeniorOfficer seniorOfficer) {
        try {

            if (seniorOfficer == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(service.save(seniorOfficer), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<SeniorOfficer>> getAll() {
        try {

            if (service.findAll().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeniorOfficer> getById(@PathVariable int id) {

        try {
            SeniorOfficer seniorOfficer = service.findById(id);
            if (seniorOfficer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(seniorOfficer, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeniorOfficerService> update(@PathVariable int id, @RequestBody SeniorOfficer seniorOfficer) {

        try {
            service.update(id, seniorOfficer);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            SeniorOfficer existingSo = service.findById(id);
            if (existingSo != null) {
                service.delete(id);
                return new ResponseEntity<String>("Senior Officer deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Senior Officer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting senior officer: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
