package com.publicvm.siburarenda.dto;

import com.publicvm.siburarenda.model.Room;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoomDto {

    private Long id;
    private String name;
    private Integer auditory;
    private String description;
    private List<String> tags;

    public static RoomDto roomToDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setAuditory(room.getMaxAuditory());
        roomDto.setName(room.getName());
        roomDto.setDescription(room.getDescription());
        roomDto.setTags(Arrays.stream(room.getTags().split(";")).collect(Collectors.toList()));
        return roomDto;
    }

    public static Room dtoToRoom(RoomDto dto) {
        Room room = new Room();
        room.setId(dto.getId());
        room.setMaxAuditory(dto.getAuditory());
        room.setName(dto.getName());
        room.setDescription(dto.getDescription());
        StringBuilder sb = new StringBuilder();
        for (String s : dto.getTags()) {
            sb.append(s);
            sb.append(";");
        }
        room.setTags(sb.toString());
        return room;
    }

}
