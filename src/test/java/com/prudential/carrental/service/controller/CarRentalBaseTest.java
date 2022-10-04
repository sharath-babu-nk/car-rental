package com.prudential.carrental.service.controller;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;

import com.prudential.carrental.service.domain.repository.BookingRepository;
import com.prudential.carrental.service.domain.repository.CarRepository;
import com.prudential.carrental.service.domain.repository.UserRepository;
import com.prudential.carrental.service.domain.service.BookingDomainService;
import com.prudential.carrental.service.domain.service.CarDomainService;
import com.prudential.carrental.service.domain.service.UserDomainService;
import com.prudential.carrental.service.dto.*;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author sharath.babu
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class CarRentalBaseTest {
	
	private final String USERS_PATH = "/users";
	private final String CARS_PATH = "/cars";
	private final String CARS_AVAILABILITY_PATH = "/cars/availability";
	private final String BOOKINGS_PATH = "/bookings";

	private RestTemplate restTemplate = new RestTemplate();

	@LocalServerPort
	private int port;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	protected UserDomainService userDomainService;
	
	@Autowired
	protected CarDomainService carDomainService;
	
	@Autowired
	protected BookingDomainService bookingDomainService;

	@Before
	public void clean() {
		bookingRepository.deleteAll();
		userRepository.deleteAll();
		carRepository.deleteAll();
	}

	protected long registerUser(String name, String email) {
		UserDTO userDTO = UserDTO.builder()
				.name(name)
				.email(email)
				.build();
		UserDTO createdUser = performCall(HttpMethod.POST, userDTO, USERS_PATH, UserDTO.class);
		return createdUser.getId();
	}

	protected long registerCar(String plateNumber, String model, Integer year, BigDecimal pricePerHour, String availableFrom, String availableTo) {
		CarDTO carDTO = CarDTO.builder()
				.plateNumber(plateNumber)
				.model(model)
				.year(year)
				.pricePerHour(pricePerHour)
				.availableFrom(availableFrom)
				.availableTo(availableTo)
				.build();
		CarDTO createdCar = performCall(HttpMethod.POST, carDTO, CARS_PATH, CarDTO.class);
		return createdCar.getId();
	}

	
	@SuppressWarnings("unchecked")
	protected List<CarDTO> searchCars(String rentFrom, String rentTo, String maxPricePerHour, String page,
			String pageSize) throws URISyntaxException {
		
		URIBuilder uriBuilder = new URIBuilder(CARS_PATH);
		uriBuilder.addParameter("rentFrom", rentFrom);
		uriBuilder.addParameter("rentTo", rentTo);
		uriBuilder.addParameter("maxPricePerHour", maxPricePerHour);
		uriBuilder.addParameter("page", page);
		uriBuilder.addParameter("pageSize", pageSize);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return performCall(HttpMethod.GET, headers, uriBuilder.build().toString(), List.class);
	}
	
	protected long createBooking(long userId, Long carId, String beginning, String end) {
		CreateBookingDTO createBookingDTO = CreateBookingDTO.builder()
				.userId(userId)
				.carId(carId)
				.beginning(beginning)
				.end(end)
				.build();
		BookingDTO createdBooking = performCall(HttpMethod.POST, createBookingDTO, BOOKINGS_PATH, BookingDTO.class);
		return createdBooking.getId();
	}

	protected <I, O> O performCall(HttpMethod httpMethod, I input, String path, Class<O> response) {
		HttpEntity<I> httpEntity = new HttpEntity<>(input);
		ResponseEntity<O> responseEntity = restTemplate.exchange("http://localhost:" + port + path, httpMethod,
				httpEntity, response);
		return responseEntity.getBody();
	}

}
