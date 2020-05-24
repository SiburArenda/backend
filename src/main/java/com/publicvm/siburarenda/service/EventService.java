package com.publicvm.siburarenda.service;

import com.publicvm.siburarenda.dto.AddEventDto;
import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.Room;
import com.publicvm.siburarenda.model.TypeOfParty;
import com.publicvm.siburarenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface EventService {

    Event addEvent(User user, Event event, List<Room> rooms);

    List<Event> getAll();

    Event activate(Event event);

    Event save(Event event);

    List<Event> getAllByUsername(User user);

    void update(Event event);

    void  fillEventWithDto(AddEventDto eventDto, Event event);

    Event getById(Long id);

    List<Event> getAcceptedEvents();

    public List<Event> getNotAcceptedEvents();
}
