package com.sales.garage.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sales.garage.entity.Car;
import com.sales.garage.entity.FuelType;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByRegistrationDateAfterAndFuelTypeAndPriceLessThan(
        LocalDate registrationDate,
        FuelType fuelType,
        BigDecimal maxPrice
    );
    @Query("SELECT DISTINCT c.make FROM Car c")
    List<String> findDistinctMake();

	List<Car> findByFuelTypeAndPriceLessThanEqual(FuelType diesel, BigDecimal bigDecimal);
}

