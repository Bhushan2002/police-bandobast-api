package com.app.policebandobastapi.model;

import com.app.policebandobastapi.enums.Enums.ForceCategory;
import com.app.policebandobastapi.enums.Enums.RecordStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Staff {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long designationId;
    private String buckleNo;
    private String mobileNo;
    private Long policeStationId;
    private Long departmentId;
    private ForceCategory forceCategory;    
    private String appPinHash;
    private RecordStatus status;

    

}
