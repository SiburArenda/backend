package com.publicvm.siburarenda.rest;

import com.publicvm.siburarenda.dto.AddEventDto;
import com.publicvm.siburarenda.dto.EventDto;
import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.Room;
import com.publicvm.siburarenda.model.Status;
import com.publicvm.siburarenda.model.TypeOfParty;
import com.publicvm.siburarenda.service.EventService;
import com.publicvm.siburarenda.service.RoomService;
import com.publicvm.siburarenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class EventRestController {

    private final EventService eventService;
    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public EventRestController(EventService eventService, UserService userService, RoomService roomService) {
        this.eventService = eventService;
        this.userService = userService;
        this.roomService = roomService;
    }

    @PostMapping("user/order")
    public ResponseEntity<Event> addEvent(@RequestBody AddEventDto eventDto) {
        Event event = new Event();
        event.setAuditory(eventDto.getAuditory());
        event.setDescription(eventDto.getComment());
        event.setDates(eventDto.getDates());
        event.setName(eventDto.getName());
        List<Room> rooms = new ArrayList<>();
        for (String s : eventDto.getRooms()) {
            Room room = roomService.findByName(s);
            if(room != null) {
                rooms.add(room);
            }
        }
        System.out.println(rooms);
        event.setRooms(rooms);
        event.setType(TypeOfParty.valueOf(eventDto.getType().toUpperCase()));
        event.setUser(userService.findByUsername(eventDto.getUser()));
        event.setCreated(new Date(System.currentTimeMillis()));
        event.setUpdated(new Date(System.currentTimeMillis()));
        event.setStatus(Status.NOT_ACTIVE);
        eventService.save(event);
        return ResponseEntity.ok(event);
    }

    @GetMapping("manage/events")
    public ResponseEntity<List<EventDto>> getAll() {
        return ResponseEntity.ok(eventService.getAll().stream().map(EventDto::eventToDto).collect(Collectors.toList()));
    }
}
