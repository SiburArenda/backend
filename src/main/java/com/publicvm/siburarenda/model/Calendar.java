package com.publicvm.siburarenda.model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calendar {

    List<DatesPair> calendar;

    public Calendar() {
        this.calendar = new ArrayList<>();
    }

    public void add(Date from, Date to, Status status) {
        DatesPair datesPair = new DatesPair(from, to, status);
        calendar.add(datesPair);
    }

    public String getJson() {
        JSONArray jsonArray = new JSONArray(calendar);
        return jsonArray.toString();
    }
}
