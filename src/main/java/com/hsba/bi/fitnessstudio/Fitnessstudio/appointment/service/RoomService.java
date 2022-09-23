package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @abakhtiari Service-Klasse für Raum-Objekt
 */

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    public Room save(Room room){
        return roomRepository.save(room);
    }
}
