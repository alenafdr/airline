package com.airline.exceptions;

public enum ErrorCode {
    ALREADY_EXISTS(1),
    PLANE_NOT_FOUND(2),
    FLIGHT_NOT_FOUND(3),
    CONNECT_TO_DATABASE(4),
    DATABASE_ERROR(5),
    ARGUMENT_NOT_VALID(6),
    USER_NOT_FOUND(7),
    NATIONALITY_NOT_FOUND(8),
    WRONG_PASSWORD(9),
    PRICE_NOT_FOUND(10),
    ORDER_NOT_FOUND(11),
    TICKET_NOT_FOUND(12),
    WRONG_PLACE(13),
    WRONG_DATE_FORMAT(14),
    CLASS_NOT_FOUND(15),
    PERIOD_NOT_FOUND(16),
    SESSION_IS_NOT_AUTHORIDED(17),
    FLIGHT_APPROVED(18)
    ;

    ErrorCode(int code) {
        this.code = code;
    }

    private final int code;
}
