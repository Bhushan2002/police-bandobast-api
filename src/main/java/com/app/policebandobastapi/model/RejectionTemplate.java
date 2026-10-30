package com.app.policebandobastapi.model;


import com.app.policebandobastapi.enums.Enums;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectionTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private String title;
    @Enumerated(EnumType.STRING)
    private Enums.TemplateType type;
    private int policeStationId;
    private String description;
    @Enumerated(EnumType.STRING)
    private Enums.Status status;


}
