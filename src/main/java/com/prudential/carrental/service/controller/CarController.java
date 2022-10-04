package com.prudential.carrental.service.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import com.prudential.carrental.service.application.service.CarService;
import com.prudential.carrental.service.dto.CarDTO;
import com.prudential.carrental.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;

/**
 * All operations with a car will be routed by this controller.
 * 
 */
@Slf4j
@RestController
@RequestMapping("/cars")
public class CarController {

	@Autowired
	CarService carService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CarDTO registerCar(@Valid @RequestBody CarDTO carDTO) throws EntityAlreadyExists,DatesNotValidException,DateFormatNotValidException {

		log.debug("Called CarController.registerOrUpdateCar with car :{}", carDTO);
		System.out.println(" registerCar carDTO --> "+carDTO.getAvailableFrom() +" TO "+ carDTO.getAvailableTo());
		return carService.registerCar(carDTO);
	}

	@GetMapping
	public List<CarDTO> searchCars(@RequestParam String rentFrom, @RequestParam String rentTo,
								   @RequestParam BigDecimal maxPricePerHour, @RequestParam(defaultValue = "0") Integer page,
								   @RequestParam(defaultValue = "5") Integer pageSize)
			throws DatesNotValidException, DateFormatNotValidException {

		log.debug("Called CarController.searchCars with parameters :{}, {}, {}", rentFrom, rentTo, maxPricePerHour);

		return carService.searchCars(rentFrom, rentTo, maxPricePerHour, page, pageSize);
	}

}
