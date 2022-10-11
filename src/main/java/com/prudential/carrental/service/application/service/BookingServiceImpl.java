package com.prudential.carrental.service.application.service;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.prudential.carrental.service.domain.entity.Booking;
import com.prudential.carrental.service.domain.entity.Car;
import com.prudential.carrental.service.domain.entity.UserEntity;
import com.prudential.carrental.service.domain.service.BookingDomainService;
import com.prudential.carrental.service.domain.service.CarDomainService;
import com.prudential.carrental.service.domain.service.UserDomainService;
import com.prudential.carrental.service.dto.BookingDTO;
import com.prudential.carrental.service.dto.CreateBookingDTO;
import com.prudential.carrental.service.exception.CarNotAvailableException;
import com.prudential.carrental.service.exception.DateFormatNotValidException;
import com.prudential.carrental.service.exception.DatesNotValidException;
import com.prudential.carrental.service.exception.EntityNotFoundException;
import com.prudential.carrental.service.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;



import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sharath.babu
 */
@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

	 @Autowired
	 BookingDomainService bookingDomainService;

	 @Autowired
	 UserDomainService userDomainService;

	 @Autowired
	 CarDomainService carDomainService;

	 @Autowired
	 ConversionService conversionService;

	@Override
	public BookingDTO createBooking(@NotNull CreateBookingDTO createBookingDTO)
			throws EntityNotFoundException, CarNotAvailableException, DatesNotValidException, DateFormatNotValidException {

		LocalDateTime beginningDateTime = Utilities.toLocalDateTime(createBookingDTO.getBeginning());
		LocalDateTime endDateTime = Utilities.toLocalDateTime(createBookingDTO.getEnd());

		if(!Utilities.isValidDates(beginningDateTime, endDateTime)) {
			log.debug("Provided dates are not valid! date from: {}, date to: {}", beginningDateTime, endDateTime);
			throw new DatesNotValidException("Provided dates are not valid!");
		}

		// get user with the provided id
		UserEntity user = userDomainService.getById(createBookingDTO.getUserId());
		// get car with the provided id
		Car car = carDomainService.getById(createBookingDTO.getCarId());
		// check car availability before creating booking
		if(!carDomainService.isAvailableWithinRentPeriod(car, beginningDateTime, endDateTime)) {
			throw new CarNotAvailableException("Car Not available within the rent period. Car ID: " + car.getCar_id());
		}

		// create booking for user and car
		Booking booking = bookingDomainService.create(new Booking(beginningDateTime, endDateTime, user, car));

		//Update available from Car to after rent endDateTime.
		car.setAvailableFrom(endDateTime);
		carDomainService.createOrUpdate(car);

		return conversionService.convert(booking, BookingDTO.class);
	}

}
