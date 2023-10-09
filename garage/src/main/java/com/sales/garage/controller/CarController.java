package com.sales.garage.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sales.garage.entity.Car;
import com.sales.garage.entity.FuelType;
import com.sales.garage.service.CarService;

import io.swagger.annotations.*;

@Api(value = "Car API", description = "API for managing cars")
@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;
    @ApiOperation(value = "Add a car to the catalog")
    @ApiResponses(value = {
    	    @ApiResponse(code = 201, message = "Car successfully added", response = Car.class),
    	    @ApiResponse(code = 400, message = "Bad Request"),
    	    @ApiResponse(code = 500, message = "Internal Server Error")
    	})
    @PostMapping
    public ResponseEntity<Car> addCarToCatalog( @ApiParam(value = "Car object to add", required = true) @RequestBody Car car) {
        if (car.getRegistrationDate().isBefore(LocalDate.of(2015, 1, 1))) {
            // Check if the car registration date is after 2015
            return ResponseEntity.badRequest().build();
        }

        Car addedCar = carService.addCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCar);
    }
    
    @ApiOperation(value = "Get cars by fuel type and max price", notes = "Retrieves cars based on their fuel type and maximum price")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cars retrieved successfully", response = Car.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @GetMapping("/by-fuel-and-price")
    public ResponseEntity<List<Car>> getCarsByFuelTypeAndMaxPrice(
    		@ApiParam(value = "Fuel type of the cars", required = true) @RequestParam FuelType fuelType,
    		@ApiParam(value = "Maximum price of the cars", required = true) @RequestParam BigDecimal maxPrice
    ) {
        List<Car> cars = carService.getCarsByFuelTypeAndMaxPrice(fuelType, maxPrice);
        return ResponseEntity.ok(cars);
    }
    
    @ApiOperation(value = "Get all car makes available in the catalog", notes = "Retrieves a list of distinct car makes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Makes retrieved successfully", response = String.class, responseContainer = "List")
            
    })
    @GetMapping("/makes")
    public ResponseEntity<List<String>> getAllMakes() {
        List<String> makes = carService.getAllMakes();
        return ResponseEntity.ok(makes);
    }

    @ApiOperation(value = "Update a car's picture", notes = "Allows updating a car's picture by car ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Car picture updated successfully", response = Car.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Car not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ApiImplicitParam(
            name = "picture",
            value = "Select a picture to upload",
            required = true,
            dataType = "file",
            paramType = "form"
        )
    @PutMapping("/{carId}/picture")
    public ResponseEntity<Car> updateCarPicture(
    		@ApiParam(value = "ID of the car to update the picture", required = true) @PathVariable Long carId,
    		@ApiParam(value = "New car picture", required = true) @RequestPart("picture") MultipartFile picture
    ) throws NoSuchAlgorithmException {
        try {
            Car updatedCar = carService.updateCarPicture(carId, picture);
            return ResponseEntity.ok(updatedCar);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

