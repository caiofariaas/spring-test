package com.dta.Test.implementations;

import com.dta.Test.model.Car;
import com.dta.Test.model.dto.CarRecordDTO;
import com.dta.Test.model.dto.CarResponseDTO;
import com.dta.Test.repositories.CarRepository;
import com.dta.Test.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarServiceImplementation implements CarService{

    @Autowired
    private CarRepository carRepository;

    @Override
    public CarResponseDTO getCarById(UUID id){
        try{
            Optional<Car> car = carRepository.findById(id);
            if(car.isPresent()){
                return new CarResponseDTO(car.get());
            }else{
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<CarResponseDTO> getAll() {
        return carRepository.findAll().stream().map(CarResponseDTO::new).toList();
    }

    @Override
    public CarResponseDTO createCar(CarRecordDTO data){
        try{
            Car newCar = new Car();
            BeanUtils.copyProperties(data, newCar);
            carRepository.save(newCar);
            return new CarResponseDTO(newCar);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public CarResponseDTO update(CarResponseDTO data) {
        try {
            Car car = carRepository.findById(data.id())
                    .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado"));

            car.setCarModel(data.carModel());
            car.setCarYear(data.carYear());
            car.setCarColor(data.carColor());
            car.setLicensePlate(data.licensePlate());

            carRepository.save(car);

            return new CarResponseDTO(car);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String delete(UUID id) {
        Optional<Car> carToDelete = carRepository.findById(id);
        if (carToDelete.isEmpty()) {
            throw new EntityNotFoundException("Carro não encontrado");
        } else {
            carRepository.delete(carToDelete.get());
        }
        return "Carro deletado com sucesso";
    }


}
