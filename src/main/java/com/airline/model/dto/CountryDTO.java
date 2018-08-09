package com.airline.model.dto;

public class CountryDTO {
    private String iso3166;
    private String name;

    public CountryDTO() {
    }

    public CountryDTO(String iso3166, String name) {
        this.iso3166 = iso3166;
        this.name = name;
    }

    public String getIso3166() {
        return iso3166;
    }

    public void setIso3166(String iso3166) {
        this.iso3166 = iso3166;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
