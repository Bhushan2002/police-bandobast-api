package com.app.policebandobastapi.model;


import com.app.policebandobastapi.enums.Enums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String designationId;
    private String buckleNo;
    private int policeStationId;
    private int departmentId;
    @Enumerated(EnumType.STRING)
    private Enums.ForceCategory forceCategory;
    @Enumerated(EnumType.STRING)
    private Enums.RecordStatus status;

}
