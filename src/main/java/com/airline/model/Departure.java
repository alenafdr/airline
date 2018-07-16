package com.airline.model;

import java.util.Date;

public class Departure {
    private Long id;
    private Date date;
    private Flight flight;

    @Override
    public String toString() {
        return "Departure" +
                " date=" + date;
    }
}
