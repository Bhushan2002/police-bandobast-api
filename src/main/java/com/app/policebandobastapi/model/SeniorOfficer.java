package com.app.policebandobastapi.model;


import com.app.policebandobastapi.enums.Enums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeniorOfficer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String DesignationId;
    private String mobile;
    private String emial;
    private int departmentId;
    private String serviceBookNo;
    private String post;
    private Date appointmentDateFrom;
    private Date appointmentDateTo;
    @Enumerated(EnumType.STRING)
    private Enums.Status status;


}
