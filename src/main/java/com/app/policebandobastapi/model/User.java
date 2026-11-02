package com.app.policebandobastapi.model;

import com.app.policebandobastapi.enums.Enums;
import com.app.policebandobastapi.enums.Enums.RecordStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Enums.Role role;
    private int headquarterId;
    private int policeStationId;
    private int seniorOfficeId;
    private int staffId;
    @Enumerated(EnumType.STRING)
    private RecordStatus status;
    private String lastLoginAt;
    private int failedAttempts;

}
