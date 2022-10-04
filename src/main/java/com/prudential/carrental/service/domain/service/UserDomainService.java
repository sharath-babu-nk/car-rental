package com.prudential.carrental.service.domain.service;

import com.prudential.carrental.service.domain.entity.UserEntity;
import com.prudential.carrental.service.exception.EntityNotFoundException;
import com.prudential.carrental.service.exception.EntityAlreadyExists;

import java.util.Optional;

import javax.validation.constraints.NotNull;


/**
 * The service that manage {@link UserEntity} entities
 * 
 * @author Sharath.babu
 */
public interface UserDomainService {

	/**
	 * Create or update user
	 *
	 * @param user {@link UserEntity}
	 *
	 * @return The {@link UserEntity}
	 * @throws EntityAlreadyExists 
	 */
	UserEntity createOrUpdate(@NotNull UserEntity user);

	/**
	 * Gets the user with the provided id
	 *
	 * @param id User id
	 *
	 * @return The {@link UserEntity}
	 *
	 * @throws EntityNotFoundException
	 */
	UserEntity getById(@NotNull Long id) throws EntityNotFoundException;
	
	/**
	 * Gets the user with the provided email
	 *
	 * @param email User email
	 *
	 * @return The {@link UserEntity}
	 *
	 */
	Optional<UserEntity> getByEmail(@NotNull String email);


}
