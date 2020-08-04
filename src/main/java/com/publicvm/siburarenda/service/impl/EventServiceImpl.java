package com.publicvm.siburarenda.service.impl;

import com.publicvm.siburarenda.dto.DatesDto;
import com.publicvm.siburarenda.dto.EventDto;
import com.publicvm.siburarenda.dto.RoomDto;
import com.publicvm.siburarenda.dto.UserDto;
import com.publicvm.siburarenda.model.*;
import com.publicvm.siburarenda.repository.EventRepository;
import com.publicvm.siburarenda.service.EventService;
import com.publicvm.siburarenda.service.RoomService;
import com.publicvm.siburarenda.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @Override
    public Event addEvent(User user, Event event, List<Room> rooms) {
        Event result = new Event();
        result.setAuditory(event.getAuditory());
        result.setDates(event.getDates());
        result.setDescription(event.getDescription());
        result.setName(event.getName());
        result.setType(event.getType());
        result.setUser(user);
        result.setRooms(rooms);
        eventRepository.save(result);
        return result;
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override //TODO(Активация по мылу.)
    public Event activate(Event event) {
        return null;
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllByUsername(User user) {
        return eventRepository.getEventByUser(user);
    }

    @Override
    @Transactional
    //TODO(победить апдейт для листа комнат)
    public void update(Event event) {
        Event updated = eventRepository.getOne(event.getId());
        updated.setStatus(event.getStatus());
        updated.setRooms(event.getRooms());
        updated.setAuditory(event.getAuditory());
        updated.setType(event.getType());
        updated.setName(event.getName());
        updated.setDescription(event.getDescription());
        updated.setUpdated(new Date(System.currentTimeMillis()));
        eventRepository.save(updated);
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public List<Event> getAcceptedEvents() {
        return eventRepository.getEventByStatus(Status.ACTIVE);
    }

    @Override
    public List<Event> getNotAcceptedEvents() {
        return eventRepository.getEventByStatus(Status.NOT_ACTIVE);
    }
}

