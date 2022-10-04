package com.prudential.carrental.service.domain.repository;

import com.prudential.carrental.service.domain.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The JPA repository for {@link Booking} entities.
 * 
 * @author sharath.babu
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
