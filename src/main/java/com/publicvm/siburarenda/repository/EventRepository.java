package com.publicvm.siburarenda.repository;

import com.publicvm.siburarenda.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> getEventByRooms(List<Room> rooms);

    List<Event> getEventByUser(User user);

    List<Event> getEventByStatus(Status status);

}
