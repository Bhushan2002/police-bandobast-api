
package com.app.policebandobastapi.model;

import com.app.policebandobastapi.enums.Enums.ForceResponseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "force_request",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_bandobast_police_station",
                        columnNames = {"bandobast_id", "police_station_id"}
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bandobast_id", nullable = false)
    private Bandobast bandobast;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "police_station_id", nullable = false)
    private PoliceStation policeStation;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    // Required force details
    private Integer srOfficer = 0;
    private Integer dcp = 0;
    private Integer srPi = 0;
    private Integer pi = 0;
    private Integer api = 0;
    private Integer hc = 0;
    private Integer pcMale = 0;
    private Integer pcFemale = 0;
    private Integer traffic = 0;
    private Integer generalForce = 0;
    private Integer homeGuard = 0;
    private Integer warden = 0;

    // Total requested personnel
    private Integer total = 0;

    // Request status
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ForceResponseStatus status = ForceResponseStatus.PENDING;
}
