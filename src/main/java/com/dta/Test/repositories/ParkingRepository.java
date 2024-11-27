package com.dta.Test.repositories;

import com.dta.Test.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {

    @Query("SELECT COUNT(e) FROM Parking e WHERE e.endTime IS NULL")
    long countCars();
}
