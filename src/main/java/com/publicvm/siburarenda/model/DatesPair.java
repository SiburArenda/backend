package com.publicvm.siburarenda.model;

import lombok.Data;

import java.util.Date;

@Data
public class DatesPair {

    Date from;

    Date to;

    Status status;

    public DatesPair(Date from, Date to, Status status) {
        this.from = from;
        this.to = to;
        this.status = status;
    }
}
