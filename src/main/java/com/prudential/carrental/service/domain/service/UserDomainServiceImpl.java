package com.prudential.carrental.service.domain.service;


import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.prudential.carrental.service.domain.entity.UserEntity;
import com.prudential.carrental.service.domain.repository.UserRepository;
import com.prudential.carrental.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The service that manage {@link UserEntity} entities
 * 
 * @author Sharath.babu
 */
@Service
public class UserDomainServiceImpl implements UserDomainService {

	@Autowired
	UserRepository userRepository;


	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public UserEntity createOrUpdate(UserEntity user) {
		return userRepository.saveAndFlush(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserEntity getById(Long id) throws EntityNotFoundException {
		return userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found ID: " + id));
	}

	@Override
	public Optional<UserEntity> getByEmail(@NotNull String email) {
		return userRepository.findByEmail(email);
	}
}
