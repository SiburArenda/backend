package com.publicvm.siburarenda.rest;

import com.publicvm.siburarenda.dto.AddEventDto;
import com.publicvm.siburarenda.dto.EventDto;
import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.Room;
import com.publicvm.siburarenda.model.Status;
import com.publicvm.siburarenda.model.TypeOfParty;
import com.publicvm.siburarenda.security.jwt.JwtTokenProvider;
import com.publicvm.siburarenda.service.EventService;
import com.publicvm.siburarenda.service.RoomService;
import com.publicvm.siburarenda.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/")
public class EventRestController {

    private final EventService eventService;
    private final UserService userService;
    private final RoomService roomService;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public EventRestController(EventService eventService, UserService userService, RoomService roomService, JwtTokenProvider tokenProvider) {
        this.eventService = eventService;
        this.userService = userService;
        this.roomService = roomService;
        this.tokenProvider = tokenProvider;
    }

    //TODO(Обработать варинант несуществующих комнат/юзера)
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
        event.setRooms(rooms);
        event.setType(TypeOfParty.valueOf(eventDto.getType().toUpperCase()));
        event.setUser(userService.findByUsername(eventDto.getUser()));
        event.setCreated(new Date(System.currentTimeMillis()));
        event.setUpdated(new Date(System.currentTimeMillis()));
        event.setStatus(Status.NOT_ACTIVE);
        eventService.save(event);
        log.info("In EventRestController addEvent event " + eventDto.getName() + " was added");
        return ResponseEntity.ok(event);
    }

    @GetMapping("manage/events")
    public ResponseEntity<List<EventDto>> getAll() {
        log.info("In EventRestController getAll OK");
        return ResponseEntity.ok(eventService.getAll().stream().map(EventDto::eventToDto).collect(Collectors.toList()));
    }

    @GetMapping("user/events")
    public ResponseEntity<List<EventDto>> getAllByUsername(@RequestHeader("Authorization") String token,
                                                           @RequestParam("username") String username) {
        token = token.substring(7);
        if(tokenProvider.getAuthentication(token).getName().equals(username)) {
            log.info("In EventRestController getAllByUsername ALL OK");
            return ResponseEntity.ok(eventService.getAllByUsername(userService.findByUsername(username))
                    .stream().map(EventDto::eventToDto).collect(Collectors.toList()));
        } else {
            log.warn("In EventRestController getAllByUsername token invalid");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ArrayList<>());
        }
    }

    @PostMapping("user/events/modify")
    public ResponseEntity<EventDto> modifyEvent(@RequestBody AddEventDto eventDto, @RequestParam Long id) {
        Event event = eventService.getById(id);
        if (event == null) {
            log.warn("In modifyEvent event with id: " + id + " doesn't exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EventDto());
        }
        eventService.fillEventWithDto(eventDto, event);
        eventService.update(event);
        log.info("In EventRestController modifyEvent was invoked");
        return ResponseEntity.ok(EventDto.eventToDto(event));
    }

    @PostMapping(value = "/manage/events/activate")
    public ResponseEntity<String> activateEvent(@RequestParam Long id, @RequestBody EventDto eventDto) {
        if (!eventDto.getId().equals(id))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        Event event = eventService.getById(id);
        if (event == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Wasn't found");
        }
        event.setStatus(Status.ACTIVE);
        eventService.save(event);
        return ResponseEntity.ok("Event with id: " + id + " was saved");
    }
}
