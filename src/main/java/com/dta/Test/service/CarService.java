package com.dta.Test.service;

import com.dta.Test.model.dto.CarRecordDTO;
import com.dta.Test.model.dto.CarResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CarService {

    CarResponseDTO getCarById(UUID id);
    List<CarResponseDTO> getAll();
    CarResponseDTO createCar(CarRecordDTO data);
    CarResponseDTO update(CarResponseDTO data);
    String delete(UUID id);
}
