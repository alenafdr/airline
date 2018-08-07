package com.airline.exceptions;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(String s) {
        super(s);
    }
}
