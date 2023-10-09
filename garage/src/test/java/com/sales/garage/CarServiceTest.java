package com.sales.garage;

import static org.mockito.Mockito.*;


import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.sales.garage.entity.Car;
import com.sales.garage.entity.FuelType;
import com.sales.garage.entity.TransmissionType;
import com.sales.garage.repository.CarRepository;
import com.sales.garage.service.CarService;

@SpringBootTest
public class CarServiceTest {
	 @MockBean
	    private CarRepository carRepository;
	    
	  	@Autowired
	    private CarService carService;
	    
	    @Test
	    public void testAddCar() {
	        Car carToAdd = new Car();
	        carToAdd.setMake("Toyota");
	        carToAdd.setModel("Camry");
	        carToAdd.setRegistrationDate(LocalDate.of(2022, 5, 15));
	        carToAdd.setPrice(new BigDecimal("25000.00"));
	        carToAdd.setFuelType(FuelType.DIESEL);
	        carToAdd.setMileage(50000);
	        carToAdd.setTransmission(TransmissionType.AUTOMATIC);
	        // Mock repository behavior
	        when(carRepository.save(carToAdd)).thenReturn(carToAdd);

	        Car addedCar = carService.addCar(carToAdd);

	        // Assertions
	        assertEquals(carToAdd, addedCar);
	        verify(carRepository, times(1)).save(carToAdd);
	    }
	    

	    @Test
	    public void testGetCarsByFuelTypeAndMaxPrice() {
	        // Prepare test data
	        FuelType fuelType = FuelType.DIESEL;
	        BigDecimal maxPrice = new BigDecimal("20000");
	        List<Car> cars = new ArrayList<>();
	        // Add test cars to the 'cars' list
	     // Initialize and add test cars to the 'cars' list
	        Car car1 = new Car();
	        car1.setMake("Toyota");
	        car1.setModel("Camry");
	        car1.setRegistrationDate(LocalDate.of(2022, 5, 15));
	        car1.setPrice(new BigDecimal("25000.00"));
	        car1.setFuelType(FuelType.DIESEL);
	        car1.setMileage(50000);
	        car1.setTransmission(TransmissionType.AUTOMATIC);

	        Car car2 = new Car();
	        car2.setMake("Honda");
	        car2.setModel("Civic");
	        car2.setRegistrationDate(LocalDate.of(2021, 8, 10));
	        car2.setPrice(new BigDecimal("22000.00"));
	        car2.setFuelType(FuelType.HYBRID);
	        car2.setMileage(40000);
	        car2.setTransmission(TransmissionType.MANUAL);

	        // Add the initialized cars to the list
	        cars.add(car1);
	        cars.add(car2);
	        // Mock repository behavior
	        when(carRepository.findByRegistrationDateAfterAndFuelTypeAndPriceLessThan(
	            LocalDate.of(2015, 1, 1),
	            fuelType,
	            maxPrice
	        )).thenReturn(cars);

	        List<Car> filteredCars = carService.getCarsByFuelTypeAndMaxPrice(fuelType, maxPrice);

	        // Assertions
	        assertEquals(cars, filteredCars);
	        verify(carRepository, times(1)).findByRegistrationDateAfterAndFuelTypeAndPriceLessThan(
	            LocalDate.of(2015, 1, 1),
	            fuelType,
	            maxPrice
	        );
	    }
	    
	    @Test
	    public void testGetAllMakes() {
	        // Test Data
	        List<String> makes = Arrays.asList("Toyota", "Honda", "Ford");

	        // Mocking the repository behavior
	        when(carRepository.findDistinctMake()).thenReturn(makes);

	        // Perform the service method
	        List<String> retrievedMakes = carService.getAllMakes();

	        // Assertions
	        assertEquals(makes, retrievedMakes);
	    }

	  
	 
}
