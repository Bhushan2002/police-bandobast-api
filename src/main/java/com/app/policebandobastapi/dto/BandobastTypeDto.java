package com.app.policebandobastapi.dto;

import com.app.policebandobastapi.enums.Enums.RecordStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class BandobastTypeDto {
    private int id;
    private String name;
    private RecordStatus status;
}
