package com.dta.Test.model.dto;

import com.dta.Test.model.Car;

import java.util.UUID;

public record CarResponseDTO(
        UUID id,
        String carModel,
        Long carYear,
        String carColor,
        String licensePlate
) {

public CarResponseDTO(Car car){
    this(car.getId(), car.getCarModel(), car.getCarYear(),
            car.getCarColor(), car.getLicensePlate());
}

}
