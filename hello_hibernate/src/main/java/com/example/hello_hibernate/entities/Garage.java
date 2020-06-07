package com.example.hello_hibernate.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Garage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	String address;

	@ManyToMany
	@JoinTable(
			name="Garage_Owner",
			joinColumns = @JoinColumn(name = "garage_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "owner_id", referencedColumnName = "id")
	)
	private List<Owner> owners;
	
	@ManyToMany
	@JoinTable(
			name="Garage_Car",
			joinColumns = @JoinColumn(name = "garage_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id")
	)
	private List<Car> cars;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Owner> getOwners() {
		return owners;
	}

	public Garage() {
	}

	public Garage(String address) {
		this.address = address;
		this.owners = new ArrayList<Owner>();
		this.cars = new ArrayList<Car>();
	}

	public void setOwners(List<Owner> owners) {
		this.owners = owners;

	}
	public List<Car> getCars() {
		return cars;
	}
	public void setCars(List<Car> cars) {
		this.cars = cars;

	}
}
