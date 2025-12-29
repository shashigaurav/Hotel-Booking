package org.hotel.hotelbookingsystem.service.imp;

import lombok.RequiredArgsConstructor;
import org.hotel.hotelbookingsystem.model.Room;
import org.hotel.hotelbookingsystem.repository.RoomRepo;
import org.hotel.hotelbookingsystem.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImp  implements RoomService {


    private final RoomRepo roomRepo;

    @Override
    public Room addRoom(Room room) {

        room.setStatus("Available");
        return roomRepo.save(room);
    }


    @Override
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepo.findById(id).orElseThrow(()->new RuntimeException("Room not found"));
    }

    @Override
    public void deleteRoom(Long id) {

        roomRepo.deleteById(id);

    }

    @Override
    public Room updateRoom(Long id, Room room) {
        Room existingRoom = roomRepo.findById(id).orElseThrow(()->new RuntimeException("Room not found"));

        existingRoom.setRoomNumber(room.getRoomNumber());
        existingRoom.setRoomType(room.getRoomType());
        existingRoom.setPrice(room.getPrice());
        existingRoom.setStatus(room.getStatus());
        return roomRepo.save(existingRoom);
    }
}
