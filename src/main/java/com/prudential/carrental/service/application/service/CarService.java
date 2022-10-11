package com.prudential.carrental.service.application.service;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.prudential.carrental.service.dto.CarDTO;
import com.prudential.carrental.service.dto.UpdateCarAvailabilitDTO;
import com.prudential.carrental.service.exception.*;

/**
 * Service that handles car related calls using domain services
 * 
 * @author Sharath.babu
 */
public interface CarService {

	CarDTO registerCar(@NotNull CarDTO carDTO) throws EntityAlreadyExists,DatesNotValidException,DateFormatNotValidException;

	List<CarDTO> searchCars(@NotNull String rentFrom, @NotNull String rentTo, @NotNull BigDecimal maxPricePerHour,
			@NotNull Integer page, @NotNull Integer pageSize) throws DatesNotValidException, DateFormatNotValidException;

	CarDTO updateCarAvailability(@NotNull Long carId, @NotNull UpdateCarAvailabilitDTO updateCarAvailabilitDTO)
			throws EntityNotFoundException, DatesNotValidException, CarHasCurrentBookings, DateFormatNotValidException;

}
