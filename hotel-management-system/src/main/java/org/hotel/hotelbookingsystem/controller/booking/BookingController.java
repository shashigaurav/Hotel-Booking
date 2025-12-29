package org.hotel.hotelbookingsystem.controller.booking;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hotel.hotelbookingsystem.config.JwtUtil;
import org.hotel.hotelbookingsystem.model.booking.Booking;
import org.hotel.hotelbookingsystem.service.booking.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final JwtUtil jwtUtil;

    @PostMapping("/create/{roomId}")
    public ResponseEntity<Booking> createBooking(@PathVariable("roomId") Long roomId, @RequestParam String checkIn, @RequestParam String checkOut, HttpServletRequest request ) {

        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);

        Booking booking = bookingService.createBooking(roomId,email, LocalDate.parse(checkIn), LocalDate.parse(checkOut));
        return ResponseEntity.ok(booking);

    }


    @GetMapping("/my")
    public ResponseEntity<List<Booking>> getUserBooking(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        return ResponseEntity.ok(bookingService.getUserBooking(email));
    }


    @PutMapping("/cancle/{id}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable("id") Long id, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        return ResponseEntity.ok(bookingService.cancelBooking(id,email));
    }


}
