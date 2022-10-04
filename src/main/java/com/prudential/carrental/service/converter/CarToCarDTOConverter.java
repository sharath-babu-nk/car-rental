package com.prudential.carrental.service.converter;

import java.time.format.DateTimeFormatter;

import com.prudential.carrental.service.domain.entity.Car;
import com.prudential.carrental.service.dto.CarDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.prudential.carrental.service.dto.CarDTO.CarDTOBuilder;




@Component
public class CarToCarDTOConverter implements Converter<Car, CarDTO> {

	@Override
	public CarDTO convert(Car car) {
		CarDTOBuilder builder = CarDTO.builder()
				.id(car.getCar_id())
				.created(car.getCreated().format(DateTimeFormatter.ISO_DATE_TIME))
				.plateNumber(car.getPlateNumber())
				.model(car.getModel())
				.year(car.getYear())
				.pricePerHour(car.getPricePerHour())
				.availableFrom(car.getAvailableFrom().toString())
				.availableTo(car.getAvailableTo().toString());

		if(car.getAvailableFrom() != null && car.getAvailableTo() != null) {
			builder
					.availableFrom(car.getAvailableFrom().format(DateTimeFormatter.ISO_DATE_TIME))
					.availableTo(car.getAvailableTo().format(DateTimeFormatter.ISO_DATE_TIME));
		}

		return builder.build();
	}

}
