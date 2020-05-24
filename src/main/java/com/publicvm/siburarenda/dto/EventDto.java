package com.publicvm.siburarenda.dto;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.TypeOfParty;
import lombok.Data;

import java.io.IOException;
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
    private List<Map<String, String>> dates;
    private String description;

    public static List<Map<String, String>> parseDateJson(String dates) {
        JsonFactory factory = new JsonFactory();
        List<Map<String, String>> list = new ArrayList<>();
        try {
            JsonParser parser = factory.createParser(dates);
            Map<String, String> map = new HashMap<>();
            while (!parser.isClosed()) {
                String fieldName = parser.nextFieldName();
                String fieldValue = parser.nextTextValue();
                if(fieldName == null && fieldValue == null) {
                    map = new HashMap<>();
                } else {
                    map.put(fieldName, fieldValue);
                }
                if(map.size() == 3) {
                    list.add(map);
                    map = new HashMap<>();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static EventDto eventToDto(Event event){
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setAuditory(event.getAuditory());
        eventDto.setName(event.getName());
        eventDto.setType(event.getType().toString());
        eventDto.setRooms(event.getRooms().stream().map(RoomDto::roomToDto).collect(Collectors.toSet()));
        eventDto.setUser(UserDto.fromUser(event.getUser()));
        eventDto.setDates(parseDateJson(event.getDates()));
        eventDto.setDescription(event.getDescription());
        return eventDto;
    }

//    public static Event dtoToEvent(EventDto eventDto) {
//        Event event = new Event();
//        event.setAuditory(eventDto.getAuditory());
//        event.setName(eventDto.getName());
//        event.setType(TypeOfParty.valueOf(eventDto.getType()));
//        event.setRooms(eventDto.getRooms().stream().map(RoomDto::dtoToRoom).collect(Collectors.toList()));
//        event.setUser(UserDto.toUser(eventDto.getUser()));
//        event.setDates();
//    }

}

