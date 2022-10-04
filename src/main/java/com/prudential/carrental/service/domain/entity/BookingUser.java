package com.prudential.carrental.service.domain.entity;



import javax.persistence.*;

/**
 * This entity represents a specific booking  for user
 *
 */

@Entity
@Table(name = "bookinguser")
public class BookingUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_user")
    private Long booking_user;

    @Column(name = "booking_id")
    private Integer booking_id;


    @Column(name = "user_id")
    private Integer user_id;

    public Long getBooking_user() {
        return booking_user;
    }

    public void setBooking_user(Long booking_user) {
        this.booking_user = booking_user;
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }


    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
