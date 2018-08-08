package com.airline.model;

public enum ParametersEnum {
    FROM_TOWN("fromTown"),
    TO_TOWN("toTown"),
    FLIGHT_NAME("flightName"),
    PLANE_NAME("planeName"),
    FROM_DATE("fromDate"),
    TO_DATE("toDate"),
    CLIENT_ID("clientId")
    ;

    private final String value;

    ParametersEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
