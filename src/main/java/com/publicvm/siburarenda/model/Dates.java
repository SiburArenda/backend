package com.publicvm.siburarenda.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Table(name = "dates")
@Entity
@Data
public class Dates extends BaseEntity{

    @Column(name = "from")
    private Date from;

    @Column(name = "to")
    private Date to;

    @ManyToOne
    @JoinTable(name = "event_dates",
            joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "date_id", referencedColumnName = "id")})
    private Event event;



}
