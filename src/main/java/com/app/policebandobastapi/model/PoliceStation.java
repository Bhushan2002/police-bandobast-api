package com.app.policebandobastapi.model;


import com.app.policebandobastapi.enums.Enums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoliceStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Enumerated(EnumType.STRING)
    private Enums.RecordStatus status;

}
