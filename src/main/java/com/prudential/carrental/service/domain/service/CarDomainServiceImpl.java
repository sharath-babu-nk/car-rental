package com.prudential.carrental.service.domain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.prudential.carrental.service.domain.entity.Booking;
import com.prudential.carrental.service.domain.entity.Car;
import com.prudential.carrental.service.domain.repository.CarRepository;
import com.prudential.carrental.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The service that manage {@link Car} entities
 * 
 * @author Sharath.babu
 */
@Service
public class CarDomainServiceImpl implements CarDomainService {

	 @Autowired
	 CarRepository carRepository;


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Car createOrUpdate(@NotNull Car car) {
		return carRepository.saveAndFlush(car);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Car getById(@NotNull Long id) throws EntityNotFoundException {
		return carRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Car not found ID: " + id));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Car> getByPlateNumber(@NotNull String plateNumber) {
		return carRepository.findByPlateNumber(plateNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public List<Car> findCarsAvailability(@NotNull LocalDateTime rentFrom, @NotNull LocalDateTime rentTo,
			BigDecimal maxPricePerHour, int page, int pageSize) {

		return carRepository
				.findCarsValidForRentDurationAndMaxPrice(rentFrom, rentTo, maxPricePerHour, PageRequest.of(page, pageSize))
				.stream()
				.filter(car -> isAvailableWithinRentPeriod(car, rentFrom, rentTo))
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public boolean isAvailableWithinRentPeriod(Car car, LocalDateTime rentFrom, LocalDateTime rentTo) {
		if(car.getAvailableFrom() == null || car.getAvailableTo() == null 
				|| !isDateTimeWithinPeriod(rentFrom, car.getAvailableFrom(), car.getAvailableTo())
				|| !isDateTimeWithinPeriod(rentTo, car.getAvailableFrom(), car.getAvailableTo())) {
			return false;
		}
		return true;
	}

	
	private boolean isDateTimeWithinPeriod(LocalDateTime dateTime, LocalDateTime rentFrom, LocalDateTime rentTo) {
		return dateTime.compareTo(rentFrom) >= 0 && dateTime.compareTo(rentTo) <= 0;
	}

	private boolean isWithinPeriod(LocalDateTime dateTime, LocalDateTime rentFrom, LocalDateTime rentTo) {
		return rentFrom.compareTo(dateTime) >= 0 && dateTime.compareTo(rentTo) <= 0;
	}


	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public boolean isBookingsFitTheNewAvailability(Car car, LocalDateTime availableFrom, LocalDateTime availableTo) {
		List<Booking> bookings = car.getBookings();
		for (Booking booking : bookings) {
			if (!isWithinPeriod(booking.getBeginning(), availableFrom, availableTo)
					|| !isWithinPeriod(booking.getEnd(), availableFrom, availableTo)) {
				return false;
			}
		}
		return true;
	}
}
