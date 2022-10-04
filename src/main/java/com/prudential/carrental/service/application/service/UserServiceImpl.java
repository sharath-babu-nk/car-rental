package com.prudential.carrental.service.application.service;

import com.prudential.carrental.service.domain.entity.UserEntity;
import com.prudential.carrental.service.domain.service.UserDomainService;
import com.prudential.carrental.service.dto.UserDTO;
import com.prudential.carrental.service.exception.EntityAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;


/**
 * 
 * @author Sharath.babu
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDomainService userDomainService;

	@Autowired
	ConversionService conversionService;

	@Override
	public UserDTO registerUser(UserDTO userDTO) throws EntityAlreadyExists {
		if (userDomainService.getByEmail(userDTO.getEmail()).isPresent()) {
			throw new EntityAlreadyExists("User Already Exists!");
		}
		UserEntity newUser = userDomainService.createOrUpdate(new UserEntity(userDTO.getName(), userDTO.getEmail()));
		return conversionService.convert(newUser, UserDTO.class);
	}

}
