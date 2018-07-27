package com.airline.exceptions;

public class FlightNotFoundException extends RuntimeException {

    public FlightNotFoundException() {
    }

    public FlightNotFoundException(String s) {
        super(s);
    }
}
