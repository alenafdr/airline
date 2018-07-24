package com.airline.model;

import java.util.Objects;
import java.util.Set;

public class Period {
    private Long id;
    private String value;
    private Set<Flight> flights;

    public Period() {
    }

    public Period(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "\nPeriod{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(id, period.id) &&
                Objects.equals(value, period.value) &&
                Objects.equals(flights, period.flights);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, value, flights);
    }
}
