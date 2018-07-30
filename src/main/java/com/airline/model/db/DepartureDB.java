package com.airline.model.db;

import com.airline.model.Departure;

import java.util.Objects;

public class DepartureDB extends Departure {


    public DepartureDB(Departure d) {
        super(d.getId(), d.getDate(), d.getFlight());
    }

    //most be compared by flight.id and date??
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departure departure = (Departure) o;
        return Objects.equals(id, departure.getId());
    }
}
