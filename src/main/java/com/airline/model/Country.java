package com.airline.model;

public class Country {
    private String iso;
    private String name;

    public Country() {
    }

    public Country(String iso, String name) {
        this.iso = iso;
        this.name = name;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return iso + "-" + name;
    }
}
