package com.prudential.carrental.service.domain.entity;

import lombok.*;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This entity represents a specific booking for user and car
 *
 */
@Entity
@Table(name = "Booking")
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Booking {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_Id", insertable = false, updatable = false, nullable = false)
	private Long booking_Id;

	@Getter
	@NotNull
	@Column(name = "Created", nullable = false)
	private LocalDateTime created = LocalDateTime.now();

	@Getter
	@NotNull
	@NonNull
	@Column(name = "Date_From", nullable = false)
	private LocalDateTime beginning;

	@Getter
	@NotNull
	@NonNull
	@Column(name = "Date_To", nullable = false)
	private LocalDateTime end;

	@Getter
	@NotNull
	@NonNull
	@ManyToOne
	@JoinTable(name = "bookinguser",
			   joinColumns = @JoinColumn(name = "booking_Id",nullable = false),
	           inverseJoinColumns = @JoinColumn(name = "user_id"))
	private UserEntity userEntity;

	@Getter
	@NotNull
	@NonNull
	@ManyToOne
	@JoinTable(name = "bookingcar",
			joinColumns = @JoinColumn(name = "booking_Id",nullable = false),
			inverseJoinColumns = @JoinColumn(name = "car_id"))
	private Car car;
}
