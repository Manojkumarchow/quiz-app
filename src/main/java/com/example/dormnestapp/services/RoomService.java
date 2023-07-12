package com.example.dormnestapp.services;

import com.example.dormnestapp.dao.RoomDao;
import com.example.dormnestapp.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private final RoomDao roomDao;

    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public ResponseEntity<List<Room>> getAllRooms() {
        try {
            return new ResponseEntity<>(roomDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> createRoom(Room room) {
        try {
            if (room.getId() == 0)
                room.setPersons(null);
            roomDao.save(room);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
        }
    }
}
