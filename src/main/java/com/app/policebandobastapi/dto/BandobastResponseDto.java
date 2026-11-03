
package com.app.policebandobastapi.dto;

import com.app.policebandobastapi.enums.Enums.BandobastStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BandobastResponseDto {

    // Basic Bandobast Details
    private Long id;

    private String orderNo;

    private String title;

    private String typeName;


    // Date and Time
    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;


    // Police Station Details
    private String requestingPoliceStationName;

    private Integer policeStationCount;


    // Manpower Details
    private Integer totalRequestedForce;

    private Integer totalAcceptedForce;

    private Integer requiredPersonnel;

    private Integer appointedEmployeesCount;


    // Approval Authority Details
    private String approvalAuthorityName;

    private String approvalAuthorityDesignation;


    // Bandobast Status
    private BandobastStatus status;


    // Creation and Decision Details
    private LocalDateTime createdAt;

    private LocalDateTime decidedAt;

    private String decisionRemark;
}
