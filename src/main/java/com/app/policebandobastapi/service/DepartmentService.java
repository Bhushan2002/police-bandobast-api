package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.DepartmentDto;

public interface DepartmentService {

    public DepartmentDto addDepartment(DepartmentDto department);

    public List<DepartmentDto> getAllDepartment();

    public DepartmentDto findById(int id);

    public DepartmentDto updateDepartment(int id, DepartmentDto department);

    public void deleteDepartment(int id);

}
