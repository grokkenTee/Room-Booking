package com.example.bookingroom.controller;

import com.example.bookingroom.entity.Room;
import com.example.bookingroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/room")
    @ResponseStatus(HttpStatus.OK)
    public List<Room> getListRoom() {
        return roomService.getAllRoom();
    }

    @GetMapping("/room/{code}")
    @ResponseStatus(HttpStatus.OK)
    public Room getRoom(@PathVariable("code") String roomCode) {
        return roomService.getRoom(roomCode);
    }

    @PostMapping("/room")
    @ResponseStatus(HttpStatus.OK)
    public Room createRoom(Room room) {
        return roomService.createRoom(room);
    }

    @PutMapping("/room/{code}")
    public ResponseEntity<?> modifyRoom(@PathVariable("code") String roomCode, Room room) {
        Room roomToModify = roomService.getRoom(roomCode);
        if (room.getRoomCode().equals(roomToModify.getRoomCode())) {
            return ResponseEntity.ok(roomService.modifyRoom(roomCode, room));
        }
        return ResponseEntity.badRequest().body("Wrong Request");
    }

    @DeleteMapping("/room/{code}")
    public ResponseEntity<?> deleteRoom(@PathVariable(name = "code") String roomCode) {
        Room roomToDelete = roomService.getRoom(roomCode);
        //TODO viết lại test chuẩn hơn
        roomService.deleteRoom(roomToDelete);
        return ResponseEntity.ok().body("Delete room successfully");
    }
}
