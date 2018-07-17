package com.airline.model;

import java.util.Date;
import java.util.Objects;

public class Departure {
    private Long id;
    private Date date;
    private Flight flight;

    public Departure() {
    }

    public Departure(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public String toString() {
        return "Departure" +
                " date=" + date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departure departure = (Departure) o;
        return Objects.equals(id, departure.id) &&
                Objects.equals(date, departure.date) &&
                Objects.equals(flight, departure.flight);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, date, flight);
    }
}
