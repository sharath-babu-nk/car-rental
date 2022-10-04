package com.prudential.carrental.service.converter;

import java.time.format.DateTimeFormatter;

import com.prudential.carrental.service.domain.entity.UserEntity;
import com.prudential.carrental.service.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class UserToUserDTOConverter implements Converter<UserEntity, UserDTO> {


	@Override
	public UserDTO convert(UserEntity user) {

		return UserDTO.builder()
				.id(user.getUser_Id())
				.created(user.getCreated().format(DateTimeFormatter.ISO_DATE_TIME))
				.name(user.getName())
				.email(user.getEmail())
				.build();
	}

}
