package com.app.policebandobastapi.dto;

import java.time.LocalDate;

import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeniorOfficerDto {
    private int id;
    private String name;
    private String designationId;
    private String mobile;
    private String email;
    private int departmentId;
    private String serviceBookNo;
    private String post;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate appointmentDateFrom;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate appointmentDateTo;
    private RecordStatus status;
}
