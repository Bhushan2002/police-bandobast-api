package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.DepartmentDto;

public interface DepartmentService {

    public DepartmentDto addDepartment(DepartmentDto department);

    public List<DepartmentDto> getAllDepartment();

    public DepartmentDto findById(Long id);

    public DepartmentDto updateDepartment(Long id, DepartmentDto department);

    public void deleteDepartment(Long id);

}
