package com.publicvm.siburarenda.service.impl;

import com.publicvm.siburarenda.dto.AddEventDto;
import com.publicvm.siburarenda.model.*;
import com.publicvm.siburarenda.repository.EventRepository;
import com.publicvm.siburarenda.service.EventService;
import com.publicvm.siburarenda.service.RoomService;
import com.publicvm.siburarenda.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
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
        eventRepository.setEventInfoById(event.getAuditory(), event.getDates(),
                event.getDescription(), event.getName(),
                event.getType(), event.getStatus(), event.getId());
    }

    @Override
    public void fillEventWithDto(AddEventDto eventDto, Event event) {
        event.setName(eventDto.getName());
        event.setAuditory(eventDto.getAuditory());
        event.setType(TypeOfParty.valueOf(eventDto.getType()));
        event.setRooms(Arrays.stream(eventDto.getRooms()).map(room -> roomService.findByName(room)).collect(Collectors.toList()));
        event.setUser(userService.findByUsername(eventDto.getUser()));
        event.setDates(eventDto.getDates());
        event.setDescription(eventDto.getComment());
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

