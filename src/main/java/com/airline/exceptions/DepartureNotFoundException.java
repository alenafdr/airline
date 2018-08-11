package com.airline.exceptions;

public class DepartureNotFoundException extends RuntimeException {
    public DepartureNotFoundException(String s) {
        super(s);
    }
}
