package com.dta.Test.controller;

import com.dta.Test.model.dto.ParkingRecordDTO;
import com.dta.Test.model.dto.ParkingResponseDTO;
import com.dta.Test.service.ParkingService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @GetMapping
    public ResponseEntity<List<ParkingResponseDTO>> getAllParking(){
        List<ParkingResponseDTO> listParking = parkingService.getAll();
        return ResponseEntity.ok(listParking);
    }

    @GetMapping("/{parkingId}")
    public ResponseEntity<ParkingResponseDTO> getById(@PathVariable UUID parkingId){
        ParkingResponseDTO parkingResponseDTO = parkingService.getById(parkingId);
        return ResponseEntity.ok(parkingResponseDTO);
    }

    @PostMapping
    public ResponseEntity<ParkingResponseDTO> registerParking(@RequestBody ParkingRecordDTO data){
        ParkingResponseDTO parkingResponseDTO = parkingService.register(data);
        return new ResponseEntity<>(parkingResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/calculate/{parkingId}")
    public ResponseEntity<BigDecimal> calculatePrice(@PathVariable UUID parkingId){
        BigDecimal price = parkingService.calculateTime(parkingId);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<ParkingResponseDTO> updateParking(@RequestBody ParkingResponseDTO data){
        ParkingResponseDTO parkingResponseDTO = parkingService.update(data);
        return new ResponseEntity<>(parkingResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{parkingId}")
    public ResponseEntity<String> deleteParking(@PathVariable UUID parkingId){
        String response = parkingService.delete(parkingId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
