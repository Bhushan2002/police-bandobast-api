package com.app.policebandobastapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.policebandobastapi.model.Employees;
import com.app.policebandobastapi.service.EmployeeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1/employees")
public class EmployeesController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    public ResponseEntity<List<Employees>> getall() {
        try {
            List<Employees> empList = service.getAll();
            if (empList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(empList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<Employees> save(@RequestBody Employees emp) {
        
        try{
            if(emp == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }else{
                service.save(emp);
                return new ResponseEntity<>(emp, HttpStatus.CREATED);
            }

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Employees> getById(@RequestParam int id) {
        try{
            Employees emp = service.findById(id);
            if (emp == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Employees> update(@PathVariable String id, @RequestBody Employees emp) {
        try{

            Employees existingEmp = service.findById(Integer.parseInt(id));
            if (existingEmp != null) {
                emp.setId(existingEmp.getId());
                service.update( existingEmp.getId(),emp);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(emp, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        
        try{
            Employees existingEmp = service.findById(id);
            if (existingEmp != null) {
                service.delete(id);
                return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>("Error deleting employee: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
