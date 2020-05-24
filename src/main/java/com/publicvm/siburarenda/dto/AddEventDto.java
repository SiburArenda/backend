package com.publicvm.siburarenda.dto;

import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.TypeOfParty;
import com.publicvm.siburarenda.service.RoomService;
import com.publicvm.siburarenda.service.UserService;
import com.publicvm.siburarenda.service.impl.RoomServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
public class AddEventDto {

    private Long id;
    private String name;
    private Integer auditory;
    private String type;
    private String[] rooms;
    private String user;
    private String dates;
    private String comment;

}
