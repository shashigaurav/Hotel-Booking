package org.hotel.hotelbookingsystem.service.imp.booking;

import lombok.RequiredArgsConstructor;
import org.hotel.hotelbookingsystem.model.Room;
import org.hotel.hotelbookingsystem.model.User;
import org.hotel.hotelbookingsystem.model.booking.Booking;
import org.hotel.hotelbookingsystem.repository.RoomRepo;
import org.hotel.hotelbookingsystem.repository.UserRepo;
import org.hotel.hotelbookingsystem.repository.booking.BookingRepo;
import org.hotel.hotelbookingsystem.service.booking.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {



    private final BookingRepo bookingRepo;
    private final UserRepo userRepo;
    private final RoomRepo roomRepo;


    @Override
   public  Booking createBooking(Long roomId, String email, LocalDate checkIn, LocalDate checkOut){
        User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        Room  room = roomRepo.findById(roomId).orElseThrow(()->new RuntimeException("Room not found"));

        if(!room.getStatus().equals("Available")){
            throw new RuntimeException("Room Status Not Available");
        }
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(user);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);

        long days = checkOut.toEpochDay() - checkIn.toEpochDay();
        booking.setTotalPrice(days * room.getPrice());

        booking.setStatus("Confirmed");
        room.setStatus("Booked");
        roomRepo.save(room);
       return bookingRepo.save(booking);


    };
    @Override
    public List<Booking> getUserBooking(String email){
         User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
         return bookingRepo.findByUser(user);
    };

    @Override
    public Booking cancelBooking(Long bookingId, String email){
       Booking booking = bookingRepo.findById(bookingId).orElseThrow(()->new RuntimeException("Booking not found"));

       if(!booking.getUser().getEmail().equals(email)){
           throw new RuntimeException("You cannot cancel someone else's booking");
       }

       booking.setStatus("Cancelled");
       Room room = booking.getRoom();
       room.setStatus("Available");
       roomRepo.save(room);
       return bookingRepo.save(booking);
    };





}
