package com.publicvm.siburarenda.service;

import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.Room;
import com.publicvm.siburarenda.model.User;

import java.util.List;

public interface EventService {

    Event addEvent(User user, Event event, List<Room> rooms);

    List<Event> getAll();

    Event activate(Event event);

    Event save(Event event);

}
