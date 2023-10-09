package com.sales.garage;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.sales.garage.entity.Car;
import com.sales.garage.entity.FuelType;
import com.sales.garage.repository.CarRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    public void testSaveCar() {
        // Create a test car
        Car carToAdd = new Car();
        carToAdd.setMake("Toyota");
        carToAdd.setModel("Camry");

        // Save the car to the database
        Car savedCar = carRepository.save(carToAdd);

        // Retrieve the saved car from the database
        Car retrievedCar = carRepository.findById(savedCar.getId()).orElse(null);

        // Assertions
        assertNotNull(retrievedCar);
        assertEquals("Toyota", retrievedCar.getMake());
        assertEquals("Camry", retrievedCar.getModel());
    }

    @Test
    public void testFindByFuelTypeAndPriceLessThanEqual() {
        // Create and save test cars with different fuel types and prices
        Car car1 = new Car();
        car1.setFuelType(FuelType.DIESEL);
        car1.setPrice(new BigDecimal("25000.00"));

        Car car2 = new Car();
        car2.setFuelType(FuelType.ELECTRIC);
        car2.setPrice(new BigDecimal("22000.00"));

        Car car3 = new Car();
        car3.setFuelType(FuelType.DIESEL);
        car3.setPrice(new BigDecimal("22000.00"));

        carRepository.saveAll(Arrays.asList(car1, car2, car3));

        // Retrieve cars by fuel type and maximum price
        List<Car> dieselCarsUnder25000 = carRepository.findByFuelTypeAndPriceLessThanEqual(FuelType.DIESEL, new BigDecimal("22000.00"));

        // Assertions
        assertEquals(1, dieselCarsUnder25000.size());
        assertEquals(FuelType.DIESEL, dieselCarsUnder25000.get(0).getFuelType());
        assertEquals(new BigDecimal("22000.00"), dieselCarsUnder25000.get(0).getPrice());
    }
}

