package com.sales.garage.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sales.garage.customException.CarNotFoundException;
import com.sales.garage.entity.Car;
import com.sales.garage.entity.FuelType;
import com.sales.garage.repository.CarRepository;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public Car addCar(Car car) {
        // Business logic to add a car
        return carRepository.save(car);
    }

    public List<Car> getCarsByFuelTypeAndMaxPrice(FuelType fuelType, BigDecimal maxPrice) {
        // Business logic to retrieve cars by fuel type and max price
        return carRepository.findByRegistrationDateAfterAndFuelTypeAndPriceLessThan(
            LocalDate.of(2015, 1, 1),
            fuelType,
            maxPrice
        );
    }

    public List<String> getAllMakes() {
        // Business logic to retrieve distinct car makes
        return carRepository.findDistinctMake();
    }

    public Car updateCarPicture(Long carId, MultipartFile picture) throws IOException, NoSuchAlgorithmException {
        // Business logic to update a car's picture
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException(carId));
        
        // Save the picture to the car object
        String pictureEncoded = Base64.getEncoder().encodeToString(picture.getBytes());
      
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(pictureEncoded.getBytes());
        String hashedString = new String(hash);
        car.setPicture(hashedString);
        
        return carRepository.save(car);
    }
}
