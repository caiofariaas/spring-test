package com.dta.Test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "Parking")
@NoArgsConstructor
@AllArgsConstructor
public class Parking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "car_duration")
    private Duration carDuration;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "fk_car_id", referencedColumnName = "id")
    private Car car;

}
