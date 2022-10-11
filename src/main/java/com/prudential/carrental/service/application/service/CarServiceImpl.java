package com.prudential.carrental.service.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.prudential.carrental.service.domain.entity.Car;
import com.prudential.carrental.service.domain.service.CarDomainService;
import com.prudential.carrental.service.dto.CarDTO;
import com.prudential.carrental.service.dto.UpdateCarAvailabilitDTO;
import com.prudential.carrental.service.exception.*;
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
public class CarServiceImpl implements CarService {

	@Autowired
	CarDomainService carDomainService;

	@Autowired
	ConversionService conversionService;

	@Override
	public CarDTO registerCar(@NotNull CarDTO carDTO) throws EntityAlreadyExists ,DatesNotValidException,DateFormatNotValidException{
		if (carDomainService.getByPlateNumber(carDTO.getPlateNumber()).isPresent()) {
			throw new EntityAlreadyExists("Car Already Exists!");
		}
		Car carModel  = new Car();
		carModel.setPlateNumber(carDTO.getPlateNumber());
		carModel.setModel(carDTO.getModel());
		carModel.setYear(carDTO.getYear());
		carModel.setPricePerHour(carDTO.getPricePerHour());

		LocalDateTime availableFrom = Utilities.toLocalDateTime(carDTO.getAvailableFrom());
		LocalDateTime availableTo = Utilities.toLocalDateTime(carDTO.getAvailableTo());
		carModel.setAvailableFrom(availableFrom);
		carModel.setAvailableTo(availableTo);
		if (!Utilities.isValidDates(availableFrom, availableTo)) {
			log.debug("Provided dates are not valid! date from: {}, date to: {}", availableFrom, availableTo);
			throw new DatesNotValidException("Provided dates are not valid!");
		}

		Car car = carDomainService
				.createOrUpdate(carModel);
		return conversionService.convert(car, CarDTO.class);
	}


	@Override
	public List<CarDTO> searchCars(@NotNull String rentFrom, @NotNull String rentTo,
			@NotNull BigDecimal maxPricePerHour, @NotNull Integer page, @NotNull Integer pageSize)
			throws DatesNotValidException, DateFormatNotValidException {
		LocalDateTime rentFromDateTime = Utilities.toLocalDateTime(rentFrom);
		LocalDateTime rentToDateTime = Utilities.toLocalDateTime(rentTo);
		
		if (!Utilities.isValidDates(rentFromDateTime, rentToDateTime)) {
			log.debug("Provided dates are not valid! date from: {}, date to: {}", rentFromDateTime, rentToDateTime);
			throw new DatesNotValidException("Provided dates are not valid!");
		}

		return carDomainService.findCarsAvailability(rentFromDateTime, rentToDateTime, maxPricePerHour, page, pageSize)
				.stream().map(car -> conversionService.convert(car, CarDTO.class)).collect(Collectors.toList());
	}

	@Override
	public CarDTO updateCarAvailability(@NotNull Long carId, UpdateCarAvailabilitDTO updateCarAvailabilitDTO)
			throws EntityNotFoundException, DatesNotValidException, CarHasCurrentBookings, DateFormatNotValidException {

		Car car = carDomainService.getById(carId);

		LocalDateTime availableFrom = Utilities.toLocalDateTime(updateCarAvailabilitDTO.getAvailableFrom());
		LocalDateTime availableTo = Utilities.toLocalDateTime(updateCarAvailabilitDTO.getAvailableTo());

		if (!Utilities.isValidDates(availableFrom, availableTo)) {
			log.debug("Provided dates are not valid! date from: {}, date to: {}", availableFrom, availableTo);
			throw new DatesNotValidException("Provided dates are not valid!");
		}

		if (!carDomainService.isBookingsFitTheNewAvailability(car, availableFrom, availableTo)) {
			log.debug("Car has bookings within the old availability! date from: {}, date to: {}", availableFrom,
					availableFrom);
			throw new CarHasCurrentBookings("Car has bookings within the old availability!");
		}

		car.setAvailableFrom(availableFrom);
		car.setAvailableTo(availableTo);
		car.setPricePerHour(updateCarAvailabilitDTO.getPricePerHour());

		return conversionService.convert(carDomainService.createOrUpdate(car), CarDTO.class);
	}
}
