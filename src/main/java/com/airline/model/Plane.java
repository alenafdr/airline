package com.airline.model;

public class Plane {
    private Long id;
    private String name;
    private int businessRow;
    private int economyRow;
    private int placesInBusinessRow;
    private int placesInEconomyRow;

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", businessRow=" + businessRow +
                ", economyRow=" + economyRow +
                ", placesInBusinessRow=" + placesInBusinessRow +
                ", placesInEconomyRow=" + placesInEconomyRow +
                '}';
    }
}