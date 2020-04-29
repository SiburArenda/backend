package com.publicvm.siburarenda.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "rooms")
public class Room extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "max_auditory")
    private Integer maxAuditory;

    @JsonIgnore
    @ManyToMany(mappedBy = "rooms", fetch = FetchType.LAZY)
    private List<Event> events;

    @Column(name = "tags")
    private String tags;

    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", maxAuditory=" + maxAuditory +
                '}';
    }
}

