package com.app.policebandobastapi.controller;


import com.app.policebandobastapi.model.SeniorOfficer;
import com.app.policebandobastapi.service.SeniorOfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/senior-officer")
public class SeniorOfficerController {


    @Autowired
    private SeniorOfficerService service;


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

            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeniorOfficer> getById(@PathVariable Integer id) {
        try {
            if (id == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeniorOfficerService> update(@PathVariable int id, @RequestBody SeniorOfficer seniorOfficer) {

        try {
            service.update(id,seniorOfficer);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
