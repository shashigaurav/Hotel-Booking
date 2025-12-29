package org.hotel.hotelbookingsystem.service.booking;

import org.hotel.hotelbookingsystem.model.booking.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    Booking createBooking(Long roomId, String email, LocalDate checkIn, LocalDate checkOut);
    List<Booking> getUserBooking(String email);

    Booking cancelBooking(Long bookingId, String email);
}
