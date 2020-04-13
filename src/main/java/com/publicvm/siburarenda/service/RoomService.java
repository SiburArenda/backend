package com.publicvm.siburarenda.service;

import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.Room;

import java.util.List;

public interface RoomService {

    Room add(Room room);

    Room delete(Room room);

    Room update(Room room);

    List<Room> getAll();

    List<Event> getRoomCalendar(Long id);

}
