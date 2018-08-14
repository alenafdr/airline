package com.airline.rest;

import com.airline.exceptions.*;
import liquibase.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.ParseException;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(PlaneNotFoundException.class)
    protected ResponseEntity<ApiError> handlePlaneNotFound(PlaneNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.PLANE_NOT_FOUND.name(), "planeName", ex.getMessage());

        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(FlightNotFoundException.class)
    protected ResponseEntity<ApiError> handleFlightNotFound(FlightNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.FLIGHT_NOT_FOUND.name(), "id", ex.getMessage());
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    protected ResponseEntity<ApiError> handleAlreadyExistsException(AlreadyExistsException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.ALREADY_EXISTS.name(), "name", ex.getMessage());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(ConnectDataBaseException.class)
    protected ResponseEntity<ApiError> handleConnectDataBaseException(ConnectDataBaseException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.CONNECT_TO_DATABASE.name(), "", ex.getMessage());
        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<ApiError> handleDataBaseException(DatabaseException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.DATABASE_ERROR.name(), "", ex.getMessage());
        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.USER_NOT_FOUND.name(), "login", ex.getMessage());
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(ChangePasswordException.class)
    protected ResponseEntity<ApiError> handleChangePasswordException(ChangePasswordException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.WRONG_PASSWORD.name(), "oldPassword", ex.getMessage());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            apiError.addSubError(ErrorCode.ARGUMENT_NOT_VALID.name(), fe.getField(), fe.getDefaultMessage());
        }
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            if ("CrossFieldValidation".equals(objectError.getCode())) {
                apiError.addSubError(ErrorCode.ARGUMENT_NOT_VALID.name(), "dates, periods", objectError.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(NationalityNotFoundException.class)
    protected ResponseEntity<ApiError> handleNationalityNotFoundException(NationalityNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.NATIONALITY_NOT_FOUND.name(), "nationality", ex.getMessage());
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(PriceNotFoundException.class)
    protected ResponseEntity<ApiError> handlePriceNotFoundException(PriceNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.PRICE_NOT_FOUND.name(), "class", ex.getMessage());
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    protected ResponseEntity<ApiError> handleOrderNotFoundException(OrderNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.ORDER_NOT_FOUND.name(), "order id", ex.getMessage());
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    protected ResponseEntity<ApiError> handleTicketNotFoundException(TicketNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.TICKET_NOT_FOUND.name(), "ticket", ex.getMessage());
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(WrongPlaceException.class)
    protected ResponseEntity<ApiError> handleWrongPlaceException(WrongPlaceException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.WRONG_PLACE.name(), "place", ex.getMessage());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(ParseException.class)
    protected ResponseEntity<ApiError> handleParseException(ParseException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.WRONG_DATE_FORMAT.name(), "date", ex.getMessage());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(ClassTypeNotFoundException.class)
    protected ResponseEntity<ApiError> handleClassTypeNotFoundException(ClassTypeNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.CLASS_NOT_FOUND.name(), "class", ex.getMessage());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(PeriodNotFoundException.class)
    protected ResponseEntity<ApiError> handlePeriodNotFoundException(PeriodNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.PERIOD_NOT_FOUND.name(), "period", ex.getMessage());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(SessionIsNotAuthorizedException.class)
    protected ResponseEntity<Object> handleSessionIsNotAuthorizedException(SessionIsNotAuthorizedException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.SESSION_IS_NOT_AUTHORIDED.name(), "", ex.getMessage());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(FlightAlreadyApprovedException.class)
    protected ResponseEntity<Object> handleFlightAlreadyApprovedException(FlightAlreadyApprovedException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.FLIGHT_APPROVED.name(), "", ex.getMessage());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

}
