package com.example.dormnestapp.controllers;

import com.example.dormnestapp.model.Room;
import com.example.dormnestapp.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rooms")
@SuppressWarnings("unused")
public class RoomController {

    @Autowired
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Room>> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping("create")
    public ResponseEntity<String> createRooms(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

}
