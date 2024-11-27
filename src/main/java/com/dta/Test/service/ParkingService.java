package com.dta.Test.service;

import com.dta.Test.model.dto.ParkingRecordDTO;
import com.dta.Test.model.dto.ParkingResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ParkingService {

    List<ParkingResponseDTO> getAll();
    ParkingResponseDTO getById(UUID id);

    ParkingResponseDTO register(ParkingRecordDTO data);

    BigDecimal calculateTime(UUID id);

    ParkingResponseDTO update(ParkingResponseDTO data);

    String delete(UUID parkingId);
}
