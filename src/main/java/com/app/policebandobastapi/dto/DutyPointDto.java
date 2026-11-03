
package com.app.policebandobastapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DutyPointDto {

    private String checkpointName;

    private Double latitude;

    private Double longitude;

    private Integer radiusMeters;

    private String note;
}
