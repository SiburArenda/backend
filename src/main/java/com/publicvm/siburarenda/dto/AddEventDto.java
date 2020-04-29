package com.publicvm.siburarenda.dto;

import com.publicvm.siburarenda.model.TypeOfParty;
import lombok.Data;
import lombok.Getter;

@Data
public class AddEventDto {

    private String name;
    private Integer auditory;
    private String type;
    private String[] rooms;
    private String user;
    private String dates;
    private String comment;

}
