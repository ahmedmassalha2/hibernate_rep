package com.example.hello_hibernate.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String licensePlate;
	private double price;
	private int year;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@ManyToMany
	private List<Garage> garageList;

	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private CarImage image;
	
	public Car(String licensePlate, double price, int year, Owner owner) {
		super();
		this.licensePlate = licensePlate;
		this.price = price;
		this.year = year;
		setOwner(owner);
		this.garageList = new ArrayList<Garage>();
	}
	public Car(String licensePlate, double price, int year, Owner owner,int id) {
		super();
		this.licensePlate = licensePlate;
		this.price = price;
		this.year = year;
		setOwner(owner);
		this.garageList = new ArrayList<Garage>();
		this.id = id;
	}
	public Car() {

	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
		this.owner.getCars().add(this);
	}

	public List<Garage> getGarageList() {
		return garageList;
	}

	public void setGarageList(List<Garage> garageList) {
		this.garageList = garageList;
	}

	public void addGarages(Garage... garages) {
		for (Garage ga : garages) {
			garageList.add(ga);
			ga.getCars().add(this); 
		}
	}

	public CarImage getImage() {
		return image;
	}

	public void setImage(CarImage image) {
		this.image = image;
		image.setCar(this);
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
