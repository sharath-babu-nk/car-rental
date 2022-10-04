package com.prudential.carrental.service.controller;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import com.prudential.carrental.service.domain.entity.Booking;
import com.prudential.carrental.service.domain.entity.Car;
import com.prudential.carrental.service.exception.EntityNotFoundException;
import org.junit.Test;


/**
 * 
 * @author sharath.babu
 *
 */
public class BookingControllerTest extends CarRentalBaseTest {

	final BigDecimal pricePerHour = BigDecimal.valueOf(10);
	// Availability
	final String AVAILABLE_FROM = "2022-10-15T21:00";
	final String AVAILABLE_TO = "2023-10-20T21:00";

	@Test
	public void canCreateBooking() throws EntityNotFoundException {
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
		// create booking
		long bookingId = createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);

		Booking booking = bookingDomainService.getById(bookingId);

		assertEquals(userId, booking.getUserEntity().getUser_Id());
		assertEquals(carId, booking.getCar().getCar_id());
		assertEquals(BOOKING_BEGINNING, booking.getBeginning().toString());
		assertEquals(BOOKING_END, booking.getEnd().toString());
	}

	@Test
	public void createBookingForCarWithoutAvailabilityDates() throws EntityNotFoundException {
		// user
		final String NAME = "User Name";
		final String EMAIL = "user@gmail.com";
		// car
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		// booking
		final String BOOKING_BEGINNING = "2022-10-04T21:00";
		final String BOOKING_END = "2022-10-25T21:00";


		// register user
		long userId = registerUser(NAME, EMAIL);
		// register car
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);

		try {
			// create booking
			createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Car Not available"));
		}
	}

	@Test
	@Transactional
	public void canCreateAnotherBookingNotIntersectsWithTheCarBookings() throws EntityNotFoundException {
		// user
		final String NAME = "Test1_Name";
		final String EMAIL = "test11@gmail.com";
		// car
		final String PLATE_NUMBER = "341AS2_1";
		final String MODEL = "BMW_11";
		final int YEAR = 2012;
		// bookings
		final String FIRST_BOOKING_BEGINNING = "2022-10-15T21:00";
		final String FIRST_BOOKING_END = "2022-10-20T21:00";
		final String SECOND_BOOKING_BEGINNING = "2022-10-15T21:00";
		final String SECOND_BOOKING_END = "2022-10-23T21:00";


		// register user
		long userId = registerUser(NAME, EMAIL);
		// register car
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);
		// create booking
		long firstBookingId = createBooking(userId, carId, FIRST_BOOKING_BEGINNING, FIRST_BOOKING_END);

		Booking firstBooking = bookingDomainService.getById(firstBookingId);

		assertEquals(userId, firstBooking.getUserEntity().getUser_Id());
		assertEquals(carId, firstBooking.getCar().getCar_id());
		assertEquals(FIRST_BOOKING_BEGINNING, firstBooking.getBeginning().toString());
		assertEquals(FIRST_BOOKING_END, firstBooking.getEnd().toString());
		
		// create another booking
		long secondBookingId = createBooking(userId, carId, SECOND_BOOKING_BEGINNING, SECOND_BOOKING_END);

		Booking secondBooking = bookingDomainService.getById(secondBookingId);

		assertEquals(userId, secondBooking.getUserEntity().getUser_Id());
		assertEquals(carId, secondBooking.getCar().getCar_id());
		assertEquals(SECOND_BOOKING_BEGINNING, secondBooking.getBeginning().toString());
		assertEquals(SECOND_BOOKING_END, secondBooking.getEnd().toString());
		
		Car car = carDomainService.getById(carId);
		assertEquals(2, car.getBookings().size());
	}
	
	@Test
	public void createBookingForCarThatHasCurrentBookingSamePeriod() throws EntityNotFoundException {
		// user
		final String NAME = "Use12_Name";
		final String EMAIL = "ssssss@gmail.com";
		// car
		final String PLATE_NUMBER = "334AS21";
		final String MODEL = "BMW+1+2";
		final int YEAR = 2011;

		// booking
		final String BOOKING_BEGINNING = "2022-10-15T21:00";
		final String BOOKING_END = "2022-10-20T21:00";

		// register user
		long userId = registerUser(NAME, EMAIL);
		// register car
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR,pricePerHour,AVAILABLE_FROM,AVAILABLE_TO);
		// create booking
		long bookingId = createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);

		Booking booking = bookingDomainService.getById(bookingId);

		assertEquals(userId, booking.getUserEntity().getUser_Id());
		assertEquals(carId, booking.getCar().getCar_id());
		assertEquals(BOOKING_BEGINNING, booking.getBeginning().toString());
		assertEquals(BOOKING_END, booking.getEnd().toString());

		// create another booking intersects with the first one
		try {
			// create booking
			createBooking(userId, carId, "2021-06-17T21:00", "2021-06-25T21:00");
			fail();
		} catch (Exception e) {

		}
	}
	
	@Test
	public void createBookingOutsideCarAvailabilityDates() throws EntityNotFoundException {
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

		try {
			// create booking
			createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Car Not available"));
		}
	}
}
