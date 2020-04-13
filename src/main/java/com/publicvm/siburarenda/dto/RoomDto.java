package com.publicvm.siburarenda.dto;

import com.publicvm.siburarenda.model.Room;
import lombok.Data;

@Data
public class RoomDto {

    private String name;
    private Integer auditory;

    public static RoomDto roomToDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setAuditory(room.getMaxAuditory());
        roomDto.setName(room.getName());
        return roomDto;
    }

}
