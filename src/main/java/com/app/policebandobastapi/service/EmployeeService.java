package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.EmployeeDto;

public interface EmployeeService {

    public EmployeeDto addEmployee(EmployeeDto employee);

    public List<EmployeeDto> getAllEmployee();

    public EmployeeDto findById(int id);

    public EmployeeDto updateEmployee(int id, EmployeeDto employee);

    public void deleteEmployee(int id);

}
