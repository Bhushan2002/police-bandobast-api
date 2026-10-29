package com.app.policebandobastapi.model;

import com.app.policebandobastapi.enums.Enums.ShiftType;
import com.app.policebandobastapi.enums.Enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private ShiftType shiftType;
    private boolean dayOffAfter24hr;
    @Enumerated(EnumType.STRING)
    private Status status;
}
