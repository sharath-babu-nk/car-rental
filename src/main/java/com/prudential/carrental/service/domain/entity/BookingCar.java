package com.prudential.carrental.service.domain.entity;



import javax.persistence.*;

/**
 * This entity represents a specific booking  for car
 *
 */

@Entity
@Table(name = "bookingcar")
public class BookingCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_car")
    private Long booking_car;

    @Column(name = "booking_id")
    private Integer booking_id;

    @Column(name = "car_id")
    private Integer car_id;

    public Long getBooking_car() {
        return booking_car;
    }

    public void setBooking_car(Long booking_car) {
        this.booking_car = booking_car;
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }


    public Integer getCar_id() {
        return car_id;
    }

    public void setCar_id(Integer car_id) {
        this.car_id = car_id;
    }
}
