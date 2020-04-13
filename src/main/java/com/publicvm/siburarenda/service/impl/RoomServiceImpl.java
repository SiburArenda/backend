package com.publicvm.siburarenda.service.impl;

import com.publicvm.siburarenda.dto.EventDto;
import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.Room;
import com.publicvm.siburarenda.model.Status;
import com.publicvm.siburarenda.repository.EventRepository;
import com.publicvm.siburarenda.repository.RoomRepository;
import com.publicvm.siburarenda.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final EventRepository eventRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, EventRepository eventRepository) {
        this.roomRepository = roomRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Room add(Room room) throws UnsupportedOperationException {
        Room result = new Room();
        if(roomRepository.findByName(room.getName()) != null) {
            log.warn("Room with name: " + room.getName() + " already exists");
            throw new UnsupportedOperationException("Room with name: " + room.getName() + " already exists");
        }
        result.setName(room.getName());
        result.setMaxAuditory(room.getMaxAuditory());
        result.setStatus(Status.ACTIVE);
        result.setUpdated(new Date(System.currentTimeMillis()));
        result.setCreated(new Date(System.currentTimeMillis()));
        roomRepository.save(result);
        log.info("IN add Room with name: {} was added", room.getName());
        return result;
    }

    @Override
    public Room delete(Room room) {
        Room result = roomRepository.findByName(room.getName());
        if(result == null) {
            log.warn("Room: " + room.getName() + " wasn't found");
            throw new UnsupportedOperationException("Room: " + room.getName() + " wasn't found");
        }
        result.setStatus(Status.DELETED);
        roomRepository.save(result);
        log.info("IN delete Room with name: {} was deleted", room.getName());
        return result;
    }

    @Override
    public Room update(Room room) {
        Room result = roomRepository.findByName(room.getName());
        if(result == null) {
            log.warn("Room: " + room.getName() + " wasn't found");
            throw new UnsupportedOperationException("Room: " + room.getName() + " wasn't found");
        }
        result.setName(room.getName());
        result.setMaxAuditory(room.getMaxAuditory());
        roomRepository.save(result);
        log.info("IN update Room with name: {} was updated", room.getName());
        return result;
    }

    @Override
    public List<Room> getAll() {
        List<Room> list = roomRepository.findAll();
        log.info("IN getAll was found {} rooms", list.size());
        return list;

    }

    @Override
    public List<Event> getRoomCalendar(Long id) {
        List<Room> rooms = new ArrayList<>();
        rooms.add(roomRepository.findById(id).get());
        return eventRepository.getEventByRooms(rooms);
    }
}
