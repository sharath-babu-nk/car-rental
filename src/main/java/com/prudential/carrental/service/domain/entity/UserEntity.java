package com.prudential.carrental.service.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * This entity represents a specific user
 *
 */
@Entity
@Table(name = "userentity", uniqueConstraints = @UniqueConstraint(name = "uc_email", columnNames = { "Email" }))
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class UserEntity {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_Id", insertable = false, updatable = false, nullable = false)
	private Long User_Id;

	@Getter
	@NotNull
	@Column(name = "Created", nullable = false)
	private LocalDateTime created = LocalDateTime.now();

	@Getter
	@NotNull
	@NonNull
	@Length(min = 1, max = 255)
	@Column(name = "Name", nullable = false)
	private String name;

	@Getter
	@NotNull
	@NonNull
	@Length(min = 1, max = 255)
	@Column(name = "Email", nullable = false)
	private String email;

	@Getter
    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<Booking> bookings;

}
