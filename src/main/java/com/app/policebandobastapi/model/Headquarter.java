package com.app.policebandobastapi.model;

import com.app.policebandobastapi.enums.Enums;
import com.app.policebandobastapi.enums.Enums.RecordStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Headquarter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long designationId;
    private String mobile;
    private String email;
    private Long departmentId;
    private String serviceBookNo;
    private String post;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate appointmentFrom;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate appointmentTo;
    @Enumerated(EnumType.STRING)
    private RecordStatus status;
}
