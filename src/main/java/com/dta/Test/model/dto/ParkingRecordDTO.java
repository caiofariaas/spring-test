package com.dta.Test.model.dto;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public record ParkingRecordDTO(
        LocalDateTime startTime,
        UUID carId
) {
}
