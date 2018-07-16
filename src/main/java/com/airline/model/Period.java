package com.airline.model;

import java.util.Set;

public class Period {
    private Long id;
    private String value;
    private Set<Flight> flights;

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
        return "Period{" +
                "value='" + value + '\'' +
                '}';
    }
}
