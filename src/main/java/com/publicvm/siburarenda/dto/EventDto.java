package com.publicvm.siburarenda.dto;

import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.TypeOfParty;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class EventDto {

    private Long id;
    private String name;
    private Integer auditory;
    private String type;
    private Set<RoomDto> rooms;
    private UserDto user;
    private List<DatesDto> dates;
    private String description;


    public static EventDto toDto(Event event){
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setAuditory(event.getAuditory());
        eventDto.setName(event.getName());
        eventDto.setType(event.getType().toString());
        eventDto.setRooms(event.getRooms().stream().map(RoomDto::toDto).collect(Collectors.toSet()));
        eventDto.setUser(UserDto.fromUser(event.getUser()));
        eventDto.setDates(event.getDates().stream().map(DatesDto::toDto).collect(Collectors.toList()));
        eventDto.setDescription(event.getDescription());
        return eventDto;
    }

    public static Event toEvent(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setAuditory(eventDto.getAuditory());
        event.setName(eventDto.getName());
        event.setType(TypeOfParty.valueOf(eventDto.getType()));
        event.setRooms(eventDto.getRooms().stream().map(RoomDto::toRoom).collect(Collectors.toList()));
        event.setUser(UserDto.toUser(eventDto.getUser()));
        event.setDates(eventDto.getDates().stream().map(DatesDto::toDates).collect(Collectors.toList()));
        event.setDescription(event.getDescription());
        return event;
    }
}

