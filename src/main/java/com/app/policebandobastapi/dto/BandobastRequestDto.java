
package com.app.policebandobastapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BandobastRequestDto {

    // Bandobast Details
    private String title;

    private Long typeId;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Long approvalAuthorityId;

    private Integer requiredPersonnel;


    // Event Location
    private String location;

    private Double latitude;

    private Double longitude;


    // Special Instructions
    private Long instructionTemplateId;

    private String specialInstructions;


    // Duty Points
    private List<DutyPointDto> checkpoints;


    // Police Station-wise Force Requests
    private List<ForceRequestDto> forceRequests;
}
