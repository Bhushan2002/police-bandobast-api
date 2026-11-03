
package com.app.policebandobastapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForceRequestDto {

    // Police Station Details
    private Long policeStationId;

    private LocalDate requestDate;


    // Required Police Personnel
    private Integer srOfficer;

    private Integer dcp;

    private Integer srPi;

    private Integer pi;

    private Integer api;

    private Integer hc;

    private Integer pcMale;

    private Integer pcFemale;

    private Integer traffic;

    private Integer generalForce;

    private Integer homeGuard;

    private Integer warden;
}
