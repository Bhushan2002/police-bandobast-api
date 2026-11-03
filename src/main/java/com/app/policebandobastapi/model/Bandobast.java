
package com.app.policebandobastapi.model;

import com.app.policebandobastapi.enums.Enums.BandobastStatus;
import com.app.policebandobastapi.enums.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bandobast")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bandobast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Bandobast Details

    @Column(name = "order_no", unique = true, length = 50)
    private String orderNo;

    @Column(nullable = false, length = 250)
    private String title;

    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @Column(name = "start_date_time", nullable = false)
    private java.time.LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private java.time.LocalDateTime endDateTime;

    @Column(name = "approval_authority_id", nullable = false)
    private Long approvalAuthorityId;

    @Column(name = "required_personnel", nullable = false)
    private Integer requiredPersonnel;


    // Audit Fields

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Event Location

    @Column(length = 500, nullable = false)
    private String location;

    @Lob
    private String route;

    private Double latitude;

    private Double longitude;


    // Special Instructions

    @Column(name = "instruction_template_id")
    private Long instructionTemplateId;

    @Lob
    private String specialInstructions;


    // Bandobast Status

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BandobastStatus status = BandobastStatus.PENDING;


    // Created By Details

    @Enumerated(EnumType.STRING)
    @Column(name = "created_by_role", nullable = false, length = 30)
    private Role createdByRole;

    @Column(name = "requesting_police_station_id")
    private Long requestingPoliceStationId;


    // Approval / Rejection Details

    @Column(name = "approved_by_id")
    private Long approvedById;

    @Column(name = "decided_at")
    private java.time.LocalDateTime decidedAt;

    @Column(name = "rejection_template_id")
    private Long rejectionTemplateId;

    @Lob
    private String decisionRemark;
}
