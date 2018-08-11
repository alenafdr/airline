package com.airline.exceptions;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String s) {
        super(s);
    }
}
