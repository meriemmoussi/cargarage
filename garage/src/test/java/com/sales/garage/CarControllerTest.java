package com.sales.garage;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.garage.controller.CarController;
import com.sales.garage.entity.Car;
import com.sales.garage.entity.FuelType;
import com.sales.garage.entity.TransmissionType;
import com.sales.garage.service.CarService;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;
    
    @Autowired
	private CarController carController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

        
    @Test
    public void testGetCarsByFuelTypeAndMaxPrice() throws Exception {
        // Test Data
        FuelType fuelType = FuelType.DIESEL;
        BigDecimal maxPrice = new BigDecimal("20000");
        List<Car> cars = new ArrayList<>();
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

        // Mocking the service method
        when(carService.getCarsByFuelTypeAndMaxPrice(fuelType, maxPrice)).thenReturn(cars);

        // Perform the GET request and assert the response
        mockMvc.perform(get("/api/cars/by-fuel-and-price")
                .param("fuelType", fuelType.name())
                .param("maxPrice", maxPrice.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // Assuming JSON response format
    }

    @Test
    public void testGetAllMakes() throws Exception {
        // Test Data
        List<String> makes = Arrays.asList("Toyota", "Honda", "Ford");

        // Mocking the service method
        when(carService.getAllMakes()).thenReturn(makes);

        // Perform the GET request and assert the response
        mockMvc.perform(get("/api/cars/makes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))); // Assuming JSON response format
    }

  
   
    // Additional tests for other controller methods
}
