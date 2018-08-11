package com.airline.model.dto;

public class PlaneDTO {
    private String name;
    private int businessRow;
    private int economyRow;
    private int placesInBusinessRow;
    private int placesInEconomyRow;

    public PlaneDTO() {
    }

    public PlaneDTO(String name,
                    int businessRow,
                    int economyRow,
                    int placesInBusinessRow,
                    int placesInEconomyRow) {
        this.name = name;
        this.businessRow = businessRow;
        this.economyRow = economyRow;
        this.placesInBusinessRow = placesInBusinessRow;
        this.placesInEconomyRow = placesInEconomyRow;
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
}
