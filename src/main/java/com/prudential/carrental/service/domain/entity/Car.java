package com.prudential.carrental.service.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * This entity represents a specific car that can be rented
 *
 */
@Entity
@Table(name = "Car", uniqueConstraints = @UniqueConstraint(name = "uc_plate_number", columnNames = { "Plate_Number" }))
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Car {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_id", insertable = false, updatable = false, nullable = false)
	private Long car_id;

	@Getter
	@Setter
	@NotNull
	@Column(name = "Created", nullable = false)
	private LocalDateTime created = LocalDateTime.now();

	@Getter
	@Setter
	@NotNull
	@NonNull
	@Length(min = 1, max = 255)
	@Column(name = "Plate_Number", nullable = false)
	private String plateNumber;

	@Getter
	@Setter
	@NotNull
	@NonNull
	@Length(min = 1, max = 100)
	@Column(name = "Model", nullable = false)
	private String model;

	@Getter
	@Setter
	@NotNull
	@NonNull
	@Column(name = "Year", nullable = false)
	private Integer year;

	@Getter
	@Setter
	@NotNull
	@NonNull
	@DecimalMin(value = "0", message = "The price per hour can not be negative!")
	@Column(name = "Price_Per_Hour")
	private BigDecimal pricePerHour;

	@Getter
	@Setter
	@NotNull
	@NonNull
	@Column(name = "Available_From")
	private LocalDateTime availableFrom;

	@Getter
	@Setter
	@NotNull
	@NonNull
	@Column(name = "Available_To")
	private LocalDateTime availableTo;

	@Getter
	@OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
	private List<Booking> bookings;
}
