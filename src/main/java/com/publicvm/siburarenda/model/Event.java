package com.publicvm.siburarenda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vladmihalcea.hibernate.type.range.PostgreSQLRangeType;
import com.vladmihalcea.hibernate.type.range.Range;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@TypeDef(
        typeClass = PostgreSQLRangeType.class,
        defaultForType = Range.class
)
@Entity
@Data
@Table(name = "events")
public class Event extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeOfParty type;

    @Column(name = "description")
    private String description;

    @Column(name = "auditory")
    private Integer auditory;

    @Column (name = "dates")
    private String dates;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "user_events",
            joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "event_rooms",
            joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "id")})
    private List<Room> rooms;
}
