package com.example.hello_hibernate;

import com.example.hello_hibernate.entities.*;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class App {
	private static Session session;

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
// Add ALL of your entities here. You can also try adding a whole package.
		configuration.addAnnotatedClass(Car.class);
		configuration.addAnnotatedClass(Owner.class);
		configuration.addAnnotatedClass(Garage.class);
		configuration.addAnnotatedClass(CarImage.class);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	private static void initializeData() throws Exception {
		Random random = new Random();
		Owner ahmed = new Owner("Ahmed", "Massalha", "ahmedmassalha2@gmail.com", "not_your_buis");
		Owner rola = new Owner("Rola", "Marie", "Rola@gmail.com", "RolaPassword");
		Owner azmi = new Owner("Azmi", "Abo Ahmad", "Azmi@gmail.com", "kingazmi");
		Owner leiel = new Owner("Leiel", "Frid", "leiel@gmail.com", "leiel");
		Owner master = new Owner("Master", "of Cars", "master@gmail.com", "inCar");
		Garage bMWGarage = new Garage("Aba Hoshi");
		Garage AUDIGarage = new Garage("Ramat Gan");
		session.save(bMWGarage);
		session.save(AUDIGarage);
		leiel.addGarages(bMWGarage, AUDIGarage);
		session.save(ahmed);
		session.save(rola);
		session.save(azmi);
		session.save(azmi);
		session.save(leiel);
		session.save(master);

		Car carAhmed = new Car("MOO-" + random.nextInt(), 100000, 2000 + random.nextInt(19), ahmed, 20);
		Car carRola = new Car("MOO-" + random.nextInt(), 100000, 1999 + random.nextInt(19), rola);
		Car carAzmi = new Car("MOO-" + random.nextInt(), 100000, 2000 + random.nextInt(19), azmi);
		Car carMast1 = new Car("MOO-" + random.nextInt(), 100000, 2000 + random.nextInt(19), master);
		Car carMast2 = new Car("MOO-" + random.nextInt(), 100000, 2000 + random.nextInt(19), master);
		//Car carMast3 = new Car("MOO-" + random.nextInt(), 100000, 2000 + random.nextInt(19), master);
		carAhmed.addGarages(bMWGarage, AUDIGarage);
		carRola.addGarages(AUDIGarage);
		carAzmi.addGarages(bMWGarage);
		carMast1.addGarages(bMWGarage, AUDIGarage);
		carMast2.addGarages(bMWGarage, AUDIGarage);
		//carMast3.addGarages(bMWGarage, AUDIGarage);
		session.save(carAhmed);
		session.save(carRola);
		session.save(carAzmi);
		session.save(carMast1);
		session.save(carMast2);
		//session.save(carMast3);
		CarImage ahmedCarImage = new CarImage(carAhmed, "company.com.ahmed.png");
		CarImage RolaImage = new CarImage(carRola, "company.com.rola.png");
		CarImage AzmiImage = new CarImage(carAzmi, "company.com.azmi.png");

		CarImage carMast1Im = new CarImage(carAzmi, "company.com.carMast1Im.png");
		CarImage carMast2Im = new CarImage(carAzmi, "company.com.carMast2Im.png");
		//CarImage carMast3Im = new CarImage(carAzmi, "company.com.carMast3Im.png");
		carAhmed.setImage(ahmedCarImage);
		carRola.setImage(RolaImage);
		carAzmi.setImage(AzmiImage);

		carMast1.setImage(carMast1Im);
		carMast2.setImage(carMast2Im);
		//carMast3.setImage(carMast3Im);

		session.save(ahmedCarImage);
		session.save(RolaImage);
		session.save(AzmiImage);
		session.save(carMast1Im);
		session.save(carMast2Im);
		//session.save(carMast3Im);

	}

	public static <T> List<T> getAll(Class<T> object) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);

		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}

	private static void printData() throws Exception {
		List<Car> cars = getAll(Car.class);
		System.out.println("\n\nCars list:");
		for (Car car : cars) {
			// String licensePlate, double price, int year
			System.out.format("ID: %d, licensePlate: %s, price: %f , year: %s\n", car.getId(), car.getLicensePlate(),
					car.getPrice(), car.getYear());
			System.out.format(" - Owner name: %s %s\n", car.getOwner().getFirstName(), car.getOwner().getLastName());
			System.out.format(" - Car image link: %s \n", car.getImage().getImageLink());
			for (Garage g : car.getGarageList()) {
				System.out.format(" - Garage address: %s\n", g.getAddress());
			}
		}

		List<Owner> owners = getAll(Owner.class);
		System.out.println("\n\nCars owners list:");
		for (Owner owner : owners) {
			System.out.format("ID: %d, Name: %s %s, Cars: %d\n", owner.getId(), owner.getFirstName(),
					owner.getLastName(), owner.getCars().size());
			for (Car car : owner.getCars()) {
				System.out.format(" - Car licensePlate: %s\n", car.getLicensePlate());
			}
		}

		List<Garage> garages = getAll(Garage.class);
		System.out.println("\n\nGarages list:");
		for (Garage g : garages) {
			System.out.format("ID: %d, Garage address: %s\n", g.getId(), g.getAddress());
			for (Owner owner : g.getOwners()) {
				System.out.format("		- Owners name: %s\n", owner.getFirstName() + " " + owner.getLastName());
			}
			System.out.println("	Cars list:");
			for (Car car : g.getCars()) {
				System.out.format("		- Car licensePlate: %s\n", car.getLicensePlate());
			}
		}
		System.out.format("\n\n");
		System.out.println("Done!");
	}

	public static void main(String[] args) {
		try {
			Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
			 SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			initializeData();
			printData();
		} catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			System.err.println("An error occured, changes have been rolled back.");
			exception.printStackTrace();
		} finally {
			session.close();
		}
	}
}