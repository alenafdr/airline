package com.airline.exceptions;

public class NationalityNotFoundException extends RuntimeException {
    public NationalityNotFoundException(String s) {
        super(s);
    }
}
