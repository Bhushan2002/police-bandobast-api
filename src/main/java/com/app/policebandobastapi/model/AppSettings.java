package com.app.policebandobastapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class AppSettings {
    @Id 
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private Long id;
    private boolean livenessCheck;
    private String gpsTagMode;
    private String topLine;
    private String shortLine;
    private String officeName;
    private String address;
    private String phone;
    private String orderPrefix;
    private String noricePrefix;
    private boolean watermarkEnabled ;
    private String watermarkOpacity;
    private String logourl;

    
}
