package com.app.policebandobastapi.dto;

import com.app.policebandobastapi.enums.Enums.RecordStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoliceStationDto {
    private int id;
    private String name;
    private String headquarterId;
    private String district;
    private String taluka;
    private String village;
    private String city;
    private String mobile;
    private String pincode;
    private String landline;
    private RecordStatus status;
}
