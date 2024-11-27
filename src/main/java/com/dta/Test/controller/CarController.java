package com.dta.Test.controller;

import com.dta.Test.model.dto.CarRecordDTO;
import com.dta.Test.model.dto.CarResponseDTO;
import com.dta.Test.repositories.CarRepository;
import com.dta.Test.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping("/{carId}")
    public ResponseEntity<CarResponseDTO> getCarById(@PathVariable UUID carId) {
        CarResponseDTO carResponseDTO = carService.getCarById(carId);
        if (carResponseDTO != null) {
            return ResponseEntity.ok(carResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CarResponseDTO>> getAllCar(){
        List<CarResponseDTO> listCar = carService.getAll();
        return ResponseEntity.ok(listCar);
    }

    @PostMapping
    public ResponseEntity<CarResponseDTO> create(@RequestBody CarRecordDTO data){
        CarResponseDTO carResponseDTO = carService.createCar(data);
        return new ResponseEntity<>(carResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CarResponseDTO> update(@RequestBody CarResponseDTO data){
        CarResponseDTO carResponseDTO = carService.update(data);
        return new ResponseEntity<>(carResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<String> delete(@PathVariable UUID carId) {
        try {
            String response = carService.delete(carId);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
