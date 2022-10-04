package com.prudential.carrental.service.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;

import com.prudential.carrental.service.domain.entity.Car;
import com.prudential.carrental.service.dto.CarDTO;
import com.prudential.carrental.service.exception.EntityNotFoundException;
import org.junit.Test;


/**
 * 
 * @author sharath.babu
 *
 */
public class CarControllerTest extends CarRentalBaseTest {

	final BigDecimal pricePerHour = BigDecimal.valueOf(10);
	// Availability
	final String AVAILABLE_FROM = "2022-10-15T21:00";
	final String AVAILABLE_TO = "2023-10-20T21:00";

	@Test
    public void canRegisterCar() throws EntityNotFoundException {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);
		// get user from db
		Car car = carDomainService.getById(carId);
		
		assertEquals(PLATE_NUMBER, car.getPlateNumber());
		assertEquals(MODEL, car.getModel());
		assertEquals(YEAR, car.getYear());
	}
	
	@Test
    public void registerUserWithPlateNumberAlreadyRegistered(){
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);
		try {
			registerCar(PLATE_NUMBER, "volvo", 2012,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);
			fail();
		}catch (Exception e) {
			assertTrue(e.getMessage().contains("Entity Already Exists"));
		}
	}
	
	@Test
    public void registerUserWithYearBefore1950() {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 1910;
		try {
			registerCar(PLATE_NUMBER, MODEL, YEAR,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);
			fail();
		}catch (Exception e) {
			assertTrue(e.getMessage().contains("Validation failed"));
		}
	}

	@Test
    public void canUpdateCarAvailability() throws EntityNotFoundException {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		final BigDecimal PRICE_PER_HOUR = BigDecimal.valueOf(100);
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);
		Car car = carDomainService.getById(carId);

		assertEquals(PLATE_NUMBER, car.getPlateNumber());
		assertEquals(MODEL, car.getModel());
		assertEquals(YEAR, car.getYear());
		assertEquals(AVAILABLE_FROM, car.getAvailableFrom().toString());
		assertEquals(AVAILABLE_TO, car.getAvailableTo().toString());
	}

	@Test
    public void canGetAvailableCars() throws EntityNotFoundException, URISyntaxException {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);

		// search for availability
		List<CarDTO> availableCars = searchCars("2022-10-17T21:00", "2022-10-25T21:00", "100", "0", "5");
		assertNotNull(availableCars);
		assertEquals(1, availableCars.size());
	}
	
	@Test
    public void createMultibleCarsAndCheckAvailability() throws URISyntaxException {

		// page 0 size 5
		List<CarDTO> availableCarsPage1 = searchCars("2022-10-17T21:00", "2022-10-25T21:00", "100", "0", "5");
		assertNotNull(availableCarsPage1);
		assertEquals(0, availableCarsPage1.size());
		// page 1 size 5
		List<CarDTO> availableCarsPage2 = searchCars("2022-10-17T21:00", "2022-10-25T21:00", "100", "1", "5");
		assertNotNull(availableCarsPage2);
		assertEquals(0, availableCarsPage2.size());
		// page 0 size 10
		List<CarDTO> availableCarsPage1Size10 = searchCars("2022-10-17T21:00", "2022-10-25T21:00", "100", "0", "10");
		assertNotNull(availableCarsPage1Size10);
		assertEquals(0, availableCarsPage1Size10.size());
	}
	
	@Test
	public void updateCarAvailabilityWithCurrentBooking() throws EntityNotFoundException {
		// user
		final String NAME = "User Name";
		final String EMAIL = "user@gmail.com";
		// car
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;

		final BigDecimal PRICE_PER_HOUR = BigDecimal.valueOf(100);
		// booking
		final String BOOKING_BEGINNING = "2022-10-25T21:00";
		final String BOOKING_END = "2022-10-26T21:00";

		// register user
		long userId = registerUser(NAME, EMAIL);
		// register car
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);
		// create booking
		createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);
		

		Car car = carDomainService.getById(carId);
		assertEquals("2023-10-20T21:00", car.getAvailableTo().toString());
	}
	
	@Test
	public void searchCarsWithCurrectBookingIntersectsWithRentPeriod() throws EntityNotFoundException, URISyntaxException {
		// user
		final String NAME = "User Name";
		final String EMAIL = "user@gmail.com";
		// car
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		// booking
		final String BOOKING_BEGINNING = "2022-10-15T21:00";
		final String BOOKING_END = "2022-10-20T21:00";

		// register user
		long userId = registerUser(NAME, EMAIL);
		// register car
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);

		// search for availability
		List<CarDTO> availableCars = searchCars("2022-10-15T21:00", "2022-10-20T21:00", "100", "0", "5");
		assertNotNull(availableCars);
		assertEquals(1, availableCars.size());
		// create booking
		createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);
		// search for availability
		List<CarDTO> availableCarsAfterBooking = searchCars("2022-10-26T21:00", "2022-10-27T21:00", "100", "0", "5");
		assertNotNull(availableCarsAfterBooking);
		assertEquals(1, availableCarsAfterBooking.size());
	}
	
	@Test
    public void carWithoutAvailabilityShouldntAppearOnSearch() throws EntityNotFoundException, URISyntaxException {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		registerCar(PLATE_NUMBER, MODEL, YEAR,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);
		
		// search for availability
		List<CarDTO> availableCars = searchCars("2022-10-17T21:00", "2022-10-25T21:00", "100", "0", "5");
		assertNotNull(availableCars);
		assertEquals(1, availableCars.size());
	}
}
