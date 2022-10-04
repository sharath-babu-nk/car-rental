package com.prudential.carrental.service.domain.repository;

import com.prudential.carrental.service.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * The JPA repository for {@link UserEntity} entities.
 * 
 * @author sharath.babu
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	/**
	 * find user with email
	 */
	Optional<UserEntity> findByEmail(String email);
}
