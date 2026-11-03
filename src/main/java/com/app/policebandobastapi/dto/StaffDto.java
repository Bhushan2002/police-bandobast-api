package com.app.policebandobastapi.dto;

import com.app.policebandobastapi.enums.Enums.ForceCategory;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {
    private Long id;
    private String name;
    private Long designationId;
    private String buckleNo;
    private String mobileNo;
    private Long policeStationId;
    private Long departmentId;
    private ForceCategory forceCategory;
    // Raw 4-digit pin from the request; the service hashes it into appPinHash
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String appPin;
    private RecordStatus status;
}
