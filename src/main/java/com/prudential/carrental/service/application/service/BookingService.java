package com.prudential.carrental.service.application.service;

import com.prudential.carrental.service.dto.BookingDTO;
import com.prudential.carrental.service.dto.CreateBookingDTO;
import com.prudential.carrental.service.exception.CarNotAvailableException;
import com.prudential.carrental.service.exception.DateFormatNotValidException;
import com.prudential.carrental.service.exception.DatesNotValidException;
import com.prudential.carrental.service.exception.EntityNotFoundException;

import javax.validation.constraints.NotNull;


/**
 * Service that handles booking related calls using domain services
 * 
 * @author sharath.babu
 */
public interface BookingService {

	BookingDTO createBooking(@NotNull CreateBookingDTO createBookingDTO) throws EntityNotFoundException,
			CarNotAvailableException, DatesNotValidException, DateFormatNotValidException;

}
