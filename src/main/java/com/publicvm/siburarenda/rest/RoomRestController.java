package com.publicvm.siburarenda.rest;

import com.publicvm.siburarenda.dto.EventDto;
import com.publicvm.siburarenda.dto.RoomDto;
import com.publicvm.siburarenda.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Controller
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
            roomService.add(RoomDto.dtoToRoom(roomDto));
        } catch (UnsupportedOperationException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
        return ResponseEntity.ok("Room with name: " + roomDto.getName() + " was added");
    }

    @PostMapping("/admin/rooms/delete")
    public ResponseEntity delete(@RequestBody RoomDto roomDto) {
        try {
            roomService.delete(RoomDto.dtoToRoom(roomDto));
        } catch (UnsupportedOperationException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
        return ResponseEntity.ok("Room with name: " + roomDto.getName() + " was deleted");
    }

    @PostMapping("/admin/rooms/update")
    public ResponseEntity update(@RequestBody RoomDto roomDto) {
        try {
            roomService.update(RoomDto.dtoToRoom(roomDto));
        } catch (UnsupportedOperationException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
        return ResponseEntity.ok("Room with name: " + roomDto.getName() + " was updated");
    }


    @GetMapping("/public/rooms")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(roomService.getAll().stream().map(RoomDto::roomToDto).collect(Collectors.toList()));
    }

    @GetMapping("/manage/rooms/{id}")
    public ResponseEntity getRoomCalendar(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomCalendar(id)
                .stream()
                .map(EventDto::eventToDto)
                .collect(Collectors.toSet()));

    }
}
