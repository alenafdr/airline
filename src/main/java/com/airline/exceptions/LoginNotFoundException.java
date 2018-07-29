package com.airline.exceptions;

public class LoginNotFoundException extends RuntimeException {
    public LoginNotFoundException(String s) {
        super(s);
    }
}
