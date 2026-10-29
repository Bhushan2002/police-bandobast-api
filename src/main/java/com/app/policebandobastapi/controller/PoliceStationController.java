package com.app.policebandobastapi.controller;


import com.app.policebandobastapi.model.Department;
import com.app.policebandobastapi.model.PoliceStation;
import com.app.policebandobastapi.service.PoliceStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/police-station")
public class PoliceStationController {
    @Autowired
    private PoliceStationService service;


    @PostMapping
    public ResponseEntity<PoliceStation> save(@RequestBody PoliceStation policeStation) {
        try {
            if (policeStation == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            service.save(policeStation);

            return new ResponseEntity<>(policeStation, HttpStatus.CREATED); 
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<List<PoliceStation>> getAll() {
        try {
            if (service.findAll().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoliceStation> getById(@PathVariable int id) {
        try {

            PoliceStation ps = service.findById(id);

            if (ps == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(ps);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<PoliceStation> update(@PathVariable int id, @RequestBody PoliceStation policeStation) {

        try {
            Optional<PoliceStation> updated = service.update(policeStation, id);
            if (updated.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated.get());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        PoliceStation ps = service.findById(id);
        try {
            if (ps != null) {
                service.delete(id);
                return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
