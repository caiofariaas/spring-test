package com.dta.Test.model.dto;

public record CarRecordDTO(
        String carModel,
        Long carYear,
        String carColor,
        String licensePlate
) {
}
