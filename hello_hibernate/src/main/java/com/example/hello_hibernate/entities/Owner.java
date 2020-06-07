package com.example.hello_hibernate.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

@Entity
public class Owner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String firstName;

	private String lastName;
	private String mail;

	private String password;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner")
	private List<Car> cars;

	@ManyToMany
	private List<Garage> garageList;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Car> getCourses() {
		return cars;
	}

	public void setCourses(List<Car> cars) {
		this.cars = cars;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public Owner(String firstName, String lastName, String mail, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.password = password;
		this.cars = new ArrayList<Car>();
		this.garageList = new ArrayList<Garage>();
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
			ga.getOwners().add(this); // ESPECIALLY THIS LINE
		}
	}

	public Owner(Owner owner) {
		this.firstName = owner.getFirstName();
		this.lastName = owner.getLastName();
		this.mail = owner.getMail();
		this.password = owner.getPassword();
		this.cars = new ArrayList<Car>(owner.getCars());
		this.garageList = new ArrayList<Garage>(owner.getGarageList());
	}

	public Owner() {

	}

}
