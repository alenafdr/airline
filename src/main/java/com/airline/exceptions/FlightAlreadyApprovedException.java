package com.airline.exceptions;

public class FlightAlreadyApprovedException extends RuntimeException {
    public FlightAlreadyApprovedException(String s) {
        super(s);
    }
}
