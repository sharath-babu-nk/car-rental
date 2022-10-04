package com.prudential.carrental.service.controller;

import javax.validation.Valid;

import com.prudential.carrental.service.application.service.UserService;
import com.prudential.carrental.service.dto.UserDTO;
import com.prudential.carrental.service.exception.EntityAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;

/**
 * All operations with a user will be routed by this controller.
 * 
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO registerUser(@Valid @RequestBody UserDTO userDTO) throws EntityAlreadyExists {

		log.debug("Called UserController.registerUser with new user :{}", userDTO);

		return userService.registerUser(userDTO);
	}

}
