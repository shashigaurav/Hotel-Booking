package org.hotel.hotelbookingsystem.service;

import org.hotel.hotelbookingsystem.model.Room;

import java.util.List;

public interface RoomService {

    Room addRoom(Room room);



    List<Room> getAllRooms();

    Room getRoomById(Long id);

    Room updateRoom(Long id, Room room);

    void deleteRoom(Long id);
}
