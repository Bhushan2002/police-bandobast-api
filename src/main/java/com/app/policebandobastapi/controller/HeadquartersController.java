package com.app.policebandobastapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.policebandobastapi.model.Headquarter;
import com.app.policebandobastapi.service.HeadquarterService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/headquarter")
public class HeadquartersController {

    @Autowired
    private HeadquarterService service;

    @PostMapping
    public ResponseEntity<Headquarter> save(@RequestBody Headquarter hq) {

        try {
            if (hq == null) {
                return new ResponseEntity<Headquarter>(HttpStatus.BAD_REQUEST);
            } else {
                service.save(hq);
                return new ResponseEntity<Headquarter>(hq, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<Headquarter>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Headquarter>> getAll() {
        try {
            List<Headquarter> hqList = service.getAll();
            if (hqList.isEmpty()) {
                return new ResponseEntity<List<Headquarter>>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<List<Headquarter>>(hqList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<List<Headquarter>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Headquarter> getById(@RequestParam int id) {
        try {
            return new ResponseEntity<Headquarter>(service.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Headquarter>(HttpStatus.NOT_FOUND);

        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Headquarter> update(@PathVariable String id, @RequestBody Headquarter hq) {

        try {

            Headquarter existingHq = service.getById(Integer.parseInt(id));
            if (existingHq != null) {
                hq.setId(existingHq.getId());
                service.update(hq, existingHq.getId());
                return new ResponseEntity<Headquarter>(hq, HttpStatus.OK);
            } else {
                return new ResponseEntity<Headquarter>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Headquarter>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            Headquarter existingHq = service.getById(id);
            if (existingHq != null) {
                service.delete(id);
                return new ResponseEntity<>("Headquarter deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Headquarter not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting headquarter: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}