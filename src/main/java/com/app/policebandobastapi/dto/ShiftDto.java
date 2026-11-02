package com.app.policebandobastapi.dto;

import java.time.LocalTime;

import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.enums.Enums.ShiftType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDto {
    private int id;
    private String name;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private ShiftType shiftType;
    private boolean dayOffAfter24hr;
    private RecordStatus status;
}
