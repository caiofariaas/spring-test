package com.dta.Test.model.dto;

import com.dta.Test.model.Parking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public record ParkingResponseDTO(
        UUID id,
        Duration carDuration,
        LocalDateTime starTime,
        LocalDateTime endTime,
        UUID carId
) {

    public ParkingResponseDTO(Parking parking) {
    this(parking.getId(), parking.getCarDuration(), parking.getStartTime(), parking.getEndTime(),
            (parking.getCar() != null ? parking.getCar().getId() : null));
    }

}
