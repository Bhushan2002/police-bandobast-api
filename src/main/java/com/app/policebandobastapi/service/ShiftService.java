package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.ShiftDto;

public interface ShiftService {

    public ShiftDto addShift(ShiftDto shift);

    public List<ShiftDto> getAllShift();

    public ShiftDto findById(Long id);

    public ShiftDto updateShift(Long id, ShiftDto shift);

    public void deleteShift(Long id);

}
