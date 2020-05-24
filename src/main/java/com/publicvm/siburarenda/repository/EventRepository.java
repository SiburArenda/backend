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

    @Modifying
    @Query("update Event e set e.auditory = ?1, e.dates = ?2, e.description = ?3, e.name = ?4, " +
            "e.type = ?5, e.status = ?6 where e.id = ?7")
    void setEventInfoById(Integer aud, String dates, String des, String name, TypeOfParty type,
                          Status status, Long id);

}
