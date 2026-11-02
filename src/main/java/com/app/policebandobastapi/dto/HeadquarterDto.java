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
public class HeadquarterDto {
    private int id;
    private String name;
    private int designationId;
    private String mobile;
    private String email;
    private int departmentId;
    private String serviceBookNo;
    private String post;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate appointmentFrom;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate appointmentTo;
    private RecordStatus status;
}
