package com.publicvm.siburarenda.rest;

import com.publicvm.siburarenda.dto.EventDto;
import com.publicvm.siburarenda.dto.RoomDto;
import com.publicvm.siburarenda.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@Controller
@Slf4j
@RequestMapping("/api")
public class RoomRestController {

    private final RoomService roomService;

    @Autowired
    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/admin/rooms/add")
    public ResponseEntity add(@RequestBody RoomDto roomDto) {
        try {
            roomService.add(RoomDto.toRoom(roomDto));
        } catch (UnsupportedOperationException ex) {
            log.error("IN RoomRestController add unsupported op ex " + ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("WHOOOPS :)");
        }
        log.info("IN RoomRestController add room was added room " + roomDto.getName() );
        return ResponseEntity.ok("Room with name: " + roomDto.getName() + " was added");
    }

    @PostMapping("/admin/rooms/delete")
    public ResponseEntity delete(@RequestBody RoomDto roomDto) {
        try {
            roomService.delete(RoomDto.toRoom(roomDto));
        } catch (UnsupportedOperationException ex) {
            log.error("IN RoomRestController delete unsupported op ex " + ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
        log.info("IN RoomRestController delete room was deleted room " + roomDto.getName() );
        return ResponseEntity.ok("Room with name: " + roomDto.getName() + " was deleted");
    }

    @PostMapping("/admin/rooms/update")
    public ResponseEntity update(@RequestBody RoomDto roomDto) {
        try {
            roomService.update(RoomDto.toRoom(roomDto));
        } catch (UnsupportedOperationException ex) {
            log.error("IN RoomRestController update unsupported op ex " + ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
        log.info("IN RoomRestController update room was updated room " + roomDto.getName() );
        return ResponseEntity.ok("Room with name: " + roomDto.getName() + " was updated");
    }


    @GetMapping("/public/rooms")
    public ResponseEntity getAll() {
        log.info("IN RoomRestController getAll was invoked");
        return ResponseEntity.ok(roomService.getAll().stream().map(RoomDto::toDto).collect(Collectors.toList()));
    }

    @GetMapping("/manage/rooms/{id}")
    public ResponseEntity getRoomCalendar(@PathVariable Long id) {
        log.info("IN RoomRestController getRoomCalendar was invoked");
        return ResponseEntity.ok(roomService.getRoomCalendar(id)
                .stream()
                .map(EventDto::toDto)
                .collect(Collectors.toSet()));

    }
}
