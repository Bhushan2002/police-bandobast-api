package com.app.policebandobastapi.dto;

import com.app.policebandobastapi.enums.Enums.ForceCategory;
import com.app.policebandobastapi.enums.Enums.RecordStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private int id;
    private String name;
    private String designationId;
    private String buckleNo;
    private int policeStationId;
    private int departmentId;
    private ForceCategory forceCategory;
    private RecordStatus status;
}
