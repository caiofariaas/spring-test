package com.dta.Test.implementations;

import com.dta.Test.model.Car;
import com.dta.Test.model.Parking;
import com.dta.Test.model.dto.ParkingRecordDTO;
import com.dta.Test.model.dto.ParkingResponseDTO;
import com.dta.Test.repositories.CarRepository;
import com.dta.Test.repositories.ParkingRepository;
import com.dta.Test.service.ParkingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingServiceImplementation implements ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private CarRepository carRepository;


    @Override
    public List<ParkingResponseDTO> getAll() {
        return parkingRepository.findAll().stream().map(ParkingResponseDTO::new).toList();
    }

    @Override
    public ParkingResponseDTO getById(UUID id) {
        try{
            Optional<Parking> parking = parkingRepository.findById(id);
            if(parking.isPresent()){
                return new ParkingResponseDTO(parking.get());
            }else{
                throw new RuntimeException();
            }
        }catch(Exception e){
            throw e;
        }
    }

    @Override
    public ParkingResponseDTO register(ParkingRecordDTO data) {
        try{
            Parking newParking = new Parking();

            BeanUtils.copyProperties(data, newParking);

            long countCars = parkingRepository.countCars();

            Optional<Car> car = carRepository.findById(data.carId());

            if(car.isEmpty()){
                throw new RuntimeException();
            }else{
                newParking.setCar(car.get());
            }

            if(countCars >= 20){
                throw new IllegalStateException("Estacionamento lotado");
            }

            newParking.setEndTime(null);


            parkingRepository.save(newParking);

            return new ParkingResponseDTO(newParking);
        }catch (Exception e){
            throw e;
        }
    }


    @Override
    public BigDecimal calculateTime(UUID id) {
        Parking parking = parkingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Não encontrado"));

        if(parking.getEndTime() == null){
            parking.setEndTime(LocalDateTime.now());
            parking.setCarDuration(durationParking(parking.getStartTime(), parking.getEndTime()));
            parkingRepository.save(parking);
        }

        if(parking.getStartTime() == null || parking.getEndTime() == null){
            throw new IllegalStateException("Horário de entrada e/ou saída não registrado.");
        }

        BigDecimal value = BigDecimal.valueOf(10);

        long durationHours = parking.getCarDuration().toHours();

        if(parking.getCarDuration().toMinutesPart() > 0){
            durationHours += 1;
        }

        return value.multiply(BigDecimal.valueOf(durationHours));
    }

    public Duration durationParking(LocalDateTime start, LocalDateTime end){
        if(start != null && end != null){
            return Duration.between(start, end);
        }else{
            throw new RuntimeException("error");
        }
    }

    @Override
    public ParkingResponseDTO update(ParkingResponseDTO data) {
        try{

            Parking parking = parkingRepository.findById(data.id()).orElseThrow(() -> new EntityNotFoundException("Não encontrado"));
            Optional<Car> car = carRepository.findById(data.carId());

            if(car.isEmpty()){
                throw new EntityNotFoundException("Carro não encontrado");
            }else{
                parking.setCar(car.get());
            }

            parking.setStartTime(data.starTime());
            parking.setEndTime(data.endTime());
            parking.setCarDuration(data.carDuration());


            parkingRepository.save(parking);

            return new ParkingResponseDTO(parking);

        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String delete(UUID parkingId) {

        Optional<Parking> deleteParking = parkingRepository.findById(parkingId);
        if(deleteParking.isEmpty()){
            throw new EntityNotFoundException("Parking not found");
        }else{
            parkingRepository.delete(deleteParking.get());
        }

        return "Parking deleted successfully";
    }
}
