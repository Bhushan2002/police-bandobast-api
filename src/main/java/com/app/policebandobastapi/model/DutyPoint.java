
package com.app.policebandobastapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "duty_point")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DutyPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bandobast_id", nullable = false)
    private Bandobast bandobast;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "radius_meters", nullable = false)
    private Integer radiusMeters = 200;

    @Column(length = 500)
    private String note;
}
