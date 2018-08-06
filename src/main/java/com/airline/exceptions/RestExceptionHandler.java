package com.airline.exceptions;

import liquibase.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(PlaneNotFoundException.class)
    protected ResponseEntity<ApiError> handlePlaneNotFound(PlaneNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.PLANE_NOT_FOUND.name(), "name", ex.getMessage());

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
    protected ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex){
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.USER_NOT_FOUND.name(), "login", ex.getMessage());
        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ChangePasswordException.class)
    protected ResponseEntity<ApiError> handleChangePasswordException(ChangePasswordException ex){
        ApiError apiError = new ApiError();
        apiError.addSubError(ErrorCode.USER_NOT_FOUND.name(), "oldPassword", ex.getMessage());
        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()){
            apiError.addSubError(ErrorCode.ARGUMENT_NOT_VALID.name(), fe.getField(), fe.getDefaultMessage());
        }

        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }
}
