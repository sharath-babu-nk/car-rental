package com.prudential.carrental.service.domain.service;

import javax.validation.constraints.NotNull;

import com.prudential.carrental.service.domain.entity.Booking;
import com.prudential.carrental.service.domain.repository.BookingRepository;
import com.prudential.carrental.service.exception.CarNotAvailableException;
import com.prudential.carrental.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The service that manage {@link Booking} entities
 * 
 * @author Sharath.babu
 */
@Service
public class BookingDomainServiceImpl implements BookingDomainService {

	 @Autowired
	 BookingRepository bookingRepository;

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public Booking create(@NotNull Booking booking) throws CarNotAvailableException {
		return bookingRepository.saveAndFlush(booking);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Booking getById(@NotNull Long id) throws EntityNotFoundException {
		return bookingRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Booking not found ID: " + id));
	}
}
