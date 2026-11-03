package com.app.policebandobastapi.dto;

import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.enums.Enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Role role;
    private Long headquarterId;
    private Long policeStationId;
    private Long seniorOfficerId;
    private Long staffId;
    private RecordStatus status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastLoginAt;
}
