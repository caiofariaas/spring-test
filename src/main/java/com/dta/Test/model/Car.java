package com.dta.Test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "Cars")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "car_model", length = 255, nullable = false)
    private String carModel;
    @Column(name = "car_year", nullable = false)
    private Long carYear;
    @Column(name = "car_color", length = 100, nullable = false)
    private String carColor;
    @Column(name = "license_plate", length = 50, nullable = false)
    private String licensePlate;

}
