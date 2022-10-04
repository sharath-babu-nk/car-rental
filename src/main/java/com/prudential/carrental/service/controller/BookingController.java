package com.prudential.carrental.service.controller;

import javax.validation.Valid;

import com.prudential.carrental.service.application.service.BookingService;
import com.prudential.carrental.service.dto.BookingDTO;
import com.prudential.carrental.service.dto.CreateBookingDTO;
import com.prudential.carrental.service.exception.CarNotAvailableException;
import com.prudential.carrental.service.exception.DateFormatNotValidException;
import com.prudential.carrental.service.exception.DatesNotValidException;
import com.prudential.carrental.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;

/**
 * All operations with a booking will be routed by this controller.
 * 
 */
@Slf4j
@RestController
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
	BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookingDTO createBooking(@Valid @RequestBody CreateBookingDTO createBookingDTO)
			throws EntityNotFoundException, CarNotAvailableException, DatesNotValidException,
			DateFormatNotValidException {

		log.debug("Called UserController.createBooking with booking :{}", createBookingDTO);

		return bookingService.createBooking(createBookingDTO);
	}

}
