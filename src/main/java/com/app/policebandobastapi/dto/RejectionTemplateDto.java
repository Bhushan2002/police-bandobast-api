package com.app.policebandobastapi.dto;

import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.enums.Enums.TemplateType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectionTemplateDto {
    private int id;
    private String title;
    private TemplateType type;
    private int policeStationId;
    private String description;
    private RecordStatus status;
}
