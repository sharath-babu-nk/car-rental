package com.prudential.carrental.service.application.service;


import com.prudential.carrental.service.dto.UserDTO;
import com.prudential.carrental.service.exception.EntityAlreadyExists;

/**
 * Service that handles user related calls commands using domain services
 * 
 * @author Sharath.babu
 */
public interface UserService {
	
	UserDTO registerUser(UserDTO userDTO) throws EntityAlreadyExists;
	

}
