package org.hotel.hotelbookingsystem.model.booking;

import jakarta.persistence.*;
import lombok.Data;
import org.hotel.hotelbookingsystem.model.Room;
import org.hotel.hotelbookingsystem.model.User;

import java.time.LocalDate;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private double totalPrice;
    private String status;



}
