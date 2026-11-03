package com.app.policebandobastapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DigitalSignatureDto {
    private Long id;
    private String imageUrl;
    private String inkColor;
    private String nameBelow;
    private String designationBelow;
}
