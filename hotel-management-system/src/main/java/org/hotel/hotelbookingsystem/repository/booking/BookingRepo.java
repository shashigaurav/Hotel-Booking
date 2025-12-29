package org.hotel.hotelbookingsystem.repository.booking;

import org.hotel.hotelbookingsystem.model.User;
import org.hotel.hotelbookingsystem.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);
}