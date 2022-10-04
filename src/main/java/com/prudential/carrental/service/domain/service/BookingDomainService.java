package com.prudential.carrental.service.domain.service;

import com.prudential.carrental.service.domain.entity.Booking;
import com.prudential.carrental.service.exception.CarNotAvailableException;
import com.prudential.carrental.service.exception.EntityNotFoundException;

import javax.validation.constraints.NotNull;


/**
 * The service that manage {@link Booking} entities
 * 
 * @author Sharath.babu
 */
public interface BookingDomainService {
	
	/**
	 * Create a new booking
	 *
	 * @param booking {@link Booking} to be created
	 *
	 * @return The {@link Booking}
	 * @throws CarNotAvailableException 
	 */
	Booking create(@NotNull Booking booking) throws CarNotAvailableException;

	/**
	 * Gets the booking with the provide id
	 *
	 * @param id booking id
	 *
	 * @return The {@link Booking}
	 *
	 * @throws EntityNotFoundException
	 */
	Booking getById(@NotNull Long id) throws EntityNotFoundException;

}
