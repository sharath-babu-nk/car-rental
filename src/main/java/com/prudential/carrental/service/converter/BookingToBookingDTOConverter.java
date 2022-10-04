package com.prudential.carrental.service.converter;

import java.time.format.DateTimeFormatter;

import com.prudential.carrental.service.domain.entity.Booking;
import com.prudential.carrental.service.dto.BookingDTO;
import com.prudential.carrental.service.dto.CarDTO;
import com.prudential.carrental.service.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;



@Component
public class BookingToBookingDTOConverter implements Converter<Booking, BookingDTO> {

	@Override
	public BookingDTO convert(Booking booking) {

		return BookingDTO.builder()
				.id(booking.getBooking_Id())
				.created(booking.getCreated().format(DateTimeFormatter.ISO_DATE_TIME))
				.beginning(booking.getBeginning().format(DateTimeFormatter.ISO_DATE_TIME))
				.end(booking.getEnd().format(DateTimeFormatter.ISO_DATE_TIME))
				.user(UserDTO.builder()
						.id(booking.getUserEntity().getUser_Id())
						.name(booking.getUserEntity().getName())
						.email(booking.getUserEntity().getEmail())
						.build())
				.car(CarDTO.builder()
						.id(booking.getCar().getCar_id())
						.plateNumber(booking.getCar().getPlateNumber())
						.model(booking.getCar().getModel())
						.year(booking.getCar().getYear())
						.build())
				.build();
	}

}
