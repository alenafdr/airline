package com.airline.model;

import java.sql.Time;
import java.util.Date;
import java.util.Set;

public class Flight {
    private Long id;
    private String name;
    private Plane plane;
    private String fromTown;
    private String toTown;
    private Time start;
    private Time duration;
    private Date fromDate;
    private Date toDate;
    private boolean approved;
    private Set<Period>periods;
    private Set<Departure> departures;
}
