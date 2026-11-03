
package com.app.policebandobastapi.service;

import com.app.policebandobastapi.dto.DutyPointDto;
import com.app.policebandobastapi.dto.DutyPointResponseDto;

import java.util.List;

public interface DutyPointService {

    public DutyPointResponseDto addDutyPoint(Long bandobastId, DutyPointDto dutyPointDto);

    public List<DutyPointResponseDto> getAllDutyPoints(Long bandobastId);

    public DutyPointResponseDto findById(Long bandobastId, Long pointId);

    public DutyPointResponseDto updateDutyPoint(Long bandobastId, Long pointId, DutyPointDto dutyPointDto);

    public void deleteDutyPoint(Long bandobastId, Long pointId);
}
