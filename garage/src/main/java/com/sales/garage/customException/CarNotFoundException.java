package com.sales.garage.customException;

public class CarNotFoundException extends RuntimeException {

	
	    public CarNotFoundException(Long carId) {
	        super("Car not found with ID: " + carId);
	    }
	

}

