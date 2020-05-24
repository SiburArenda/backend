package com.publicvm.siburarenda.rest;

import com.publicvm.siburarenda.dto.EventDto;
import com.publicvm.siburarenda.dto.UserDto;
import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.User;
import com.publicvm.siburarenda.service.EventService;
import com.publicvm.siburarenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller user connected requestst.
 *
 * @author Valera
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/api/manage/")
public class ManageRestController {

    private final UserService userService;

    private final EventService eventService;

    @Autowired
    public ManageRestController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping(value = "events/accepted")
    public ResponseEntity<List<EventDto>> getAcceptedEvents() {
        return ResponseEntity.ok(eventService.getAcceptedEvents()
                .stream()
                .map(EventDto::eventToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "events/acceptable")
    public ResponseEntity<List<EventDto>> getNotAcceptedEvents() {
        return ResponseEntity.ok(eventService.getNotAcceptedEvents()
                .stream()
                .map(EventDto::eventToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.getAll();
        users.sort(Comparator.comparing(User::eventsCount, Comparator.reverseOrder()));
        return ResponseEntity.ok(users
                .stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList()));
    }
}
