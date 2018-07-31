package com.airline.exceptions;

public class PlaneNotFoundException extends RuntimeException {

    public PlaneNotFoundException(String s) {
        super(s);
    }
}
