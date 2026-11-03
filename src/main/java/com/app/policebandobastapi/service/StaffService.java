package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.StaffDto;

public interface StaffService {

    public StaffDto addStaff(StaffDto staff);

    public List<StaffDto> getAllStaff();

    public StaffDto findById(Long id);

    public StaffDto updateStaff(Long id, StaffDto staff);

    public void deleteStaff(Long id);

}
