package com.airline.exceptions;

public enum ErrorCode {
    ALREADY_EXISTS(1),
    PLANE_NOT_FOUND(2),
    FLIGHT_NOT_FOUND(3),
    CONNECT_TO_DATABASE(4),
    DATABASE_ERROR(5),
    ARGUMENT_NOT_VALID(6),
    USER_NOT_FOUND(7)
    ;

    ErrorCode(int code) {
        this.code = code;
    }

    private final int code;
}
