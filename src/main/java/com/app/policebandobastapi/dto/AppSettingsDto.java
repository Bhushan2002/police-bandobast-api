package com.app.policebandobastapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppSettingsDto {
    private Long id;
    private boolean livenessCheck;
    private String gpsTagMode;
    private String topLine;
    private String shortName;
    private String officeName;
    private String address;
    private String phone;
    private String orderPrefix;
    private String noticePrefix;
    private boolean watermarkEnabled;
    private String watermarkOpacity;
    private String logoUrl;
}
