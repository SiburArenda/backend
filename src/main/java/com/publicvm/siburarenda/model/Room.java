package com.publicvm.siburarenda.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "rooms")
public class Room extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "max_auditory")
    private Integer maxAuditory;

    @ManyToOne
    @JoinTable(name = "event_rooms",
            joinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")})
    private Event event;
}
