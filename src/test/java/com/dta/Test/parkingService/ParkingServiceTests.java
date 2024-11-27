package com.dta.Test.parkingService;

import com.dta.Test.implementations.ParkingServiceImplementation;
import com.dta.Test.model.Car;
import com.dta.Test.model.Parking;
import com.dta.Test.model.dto.ParkingRecordDTO;
import com.dta.Test.model.dto.ParkingResponseDTO;
import com.dta.Test.repositories.CarRepository;
import com.dta.Test.repositories.ParkingRepository;
import com.dta.Test.service.ParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParkingServiceTests {

    @Mock
    private ParkingRepository parkingRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private ParkingServiceImplementation parkingService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllParking(){
        when(parkingRepository.findAll()).thenReturn(List.of(new Parking()));
        List<ParkingResponseDTO> responseDTOS = parkingService.getAll();
        assertNotNull(responseDTOS);
        assertEquals(1, responseDTOS.size());
        verify(parkingRepository, times(1)).findAll();
    }

    @Test
    void saveParking(){
        UUID carId = UUID.randomUUID();

        ParkingRecordDTO parking = new ParkingRecordDTO(LocalDateTime.now(), carId);
        Car car = new Car();
        car.setId(carId);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(parkingRepository.save(any(Parking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ParkingResponseDTO responseDTO = parkingService.register(parking);

        assertNotNull(responseDTO);
        assertEquals(parking.startTime(), responseDTO.starTime());
        assertEquals(parking.carId(), responseDTO.carId());
        verify(carRepository, times(1)).findById(carId);
        verify(parkingRepository, times(1)).save(any(Parking.class));
    }

    @Test
    void parkingCapacity(){
        List<ParkingResponseDTO> parkingResponseDTOS = parkingService.getAll();

        List<UUID> cars = parkingResponseDTOS.stream().map(
                        ParkingResponseDTO::carId)
                .toList();

        assertTrue(cars.size() <= 20);
    }

    @Test
    void timeIsInHour() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);

        Duration result = parkingService.durationParking(start, end);

        assertInstanceOf(Duration.class, result, "O resultado deve ser uma instância de Duration");

        long hours = result.toHours();

        assertTrue(hours >= 0, "O tempo de duração precisa estar em Horas!");
    }

    @Test
    void calculatePricePerTime(){
        UUID parkingId = UUID.randomUUID();

        LocalDateTime startTime = LocalDateTime.now().minusHours(3);
        LocalDateTime endTime = LocalDateTime.now();

        Parking parking = new Parking();
        parking.setId(parkingId);
        parking.setStartTime(startTime);
        parking.setEndTime(endTime);
        parking.setCarDuration(Duration.between(startTime, endTime));

        // Quando utilizar tal método, ele precisa me retornar um Optional de Parking

        when(parkingRepository.findById(parkingId)).thenReturn(Optional.of(parking));

        BigDecimal calculado = parkingService.calculateTime(parkingId);

        BigDecimal esperado = BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(3));
        assertEquals(esperado, calculado, "O valor calculado deve ser de R$40,00 para 3h30min");
    }

    @Test
    void carInfoIsPresent(){
        boolean verify = carRepository.findAll().stream().allMatch(car ->
                        car.getCarModel() != null &&
                        car.getCarYear() != null &&
                        car.getCarColor() != null &&
                        car.getLicensePlate() != null
        );
        assertTrue(verify);
    }
}
