package com.publicvm.siburarenda.service.impl;

import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.Room;
import com.publicvm.siburarenda.model.TypeOfParty;
import com.publicvm.siburarenda.model.User;
import com.publicvm.siburarenda.repository.EventRepository;
import com.publicvm.siburarenda.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

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

    @Override
    public Event activate(Event event) {
        return null;
    }
}
