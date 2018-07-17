package com.airline.model;

import java.util.Objects;

public class Plane {
    private Long id;
    private String name;
    private int businessRow;
    private int economyRow;
    private int placesInBusinessRow;
    private int placesInEconomyRow;

    public Plane() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBusinessRow() {
        return businessRow;
    }

    public void setBusinessRow(int businessRow) {
        this.businessRow = businessRow;
    }

    public int getEconomyRow() {
        return economyRow;
    }

    public void setEconomyRow(int economyRow) {
        this.economyRow = economyRow;
    }

    public int getPlacesInBusinessRow() {
        return placesInBusinessRow;
    }

    public void setPlacesInBusinessRow(int placesInBusinessRow) {
        this.placesInBusinessRow = placesInBusinessRow;
    }

    public int getPlacesInEconomyRow() {
        return placesInEconomyRow;
    }

    public void setPlacesInEconomyRow(int placesInEconomyRow) {
        this.placesInEconomyRow = placesInEconomyRow;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return businessRow == plane.businessRow &&
                economyRow == plane.economyRow &&
                placesInBusinessRow == plane.placesInBusinessRow &&
                placesInEconomyRow == plane.placesInEconomyRow &&
                Objects.equals(id, plane.id) &&
                Objects.equals(name, plane.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, businessRow, economyRow, placesInBusinessRow, placesInEconomyRow);
    }
}