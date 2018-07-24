package com.airline.model;

import java.util.Date;
import java.util.Objects;

public class Departure {
    protected Long id;
    protected Date date;
    protected Flight flight;

    public Departure() {
    }

    public Departure(Long id) {
        this.id = id;
    }

    public Departure(Date date) {
        this.date = date;
    }

    public Departure(Date date, Flight flight) {
        this.date = date;
        this.flight = flight;
    }

    public Departure(Long id, Date date, Flight flight) {
        this.id = id;
        this.date = date;
        this.flight = flight;
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
        return "\nDeparture{" +
                "id=" + id +
                ", date=" + date +
                ", flight=" + flight.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departure departure = (Departure) o;
        return Objects.equals(id, departure.id) &&
                Objects.equals(date, departure.date) &&
                Objects.equals(flight.getId(), departure.flight.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, flight);
    }
}
