package com.app.policebandobastapi.model;

import com.app.policebandobastapi.enums.Enums.RecordStatus;

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
public class BandobastType {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private RecordStatus status;
}
