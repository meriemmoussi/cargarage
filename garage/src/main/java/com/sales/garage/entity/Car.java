package com.sales.garage.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
@ApiModel(description = "Car entity representing a vehicle in the catalog")
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String make;
    private String model;
    private LocalDate registrationDate;
    private BigDecimal price;
    private FuelType fuelType;
    private int mileage;
    private TransmissionType transmission;
    private String picture;
    
    // Getters and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public FuelType getFuelType() {
		return fuelType;
	}
	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}
	public int getMileage() {
		return mileage;
	}
	public void setMileage(int mileage) {
		this.mileage = mileage;
	}
	public TransmissionType getTransmission() {
		return transmission;
	}
	public void setTransmission(TransmissionType transmission) {
		this.transmission = transmission;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

   
}
