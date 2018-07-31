package com.airline.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ApiError {

    private List<ApiSubError> errors;

    public ApiError() {
        errors = new ArrayList<>();
    }

    public List<ApiSubError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiSubError> errors) {
        this.errors = errors;
    }

    public void addSubError(String code, String field, String message) {
        errors.add(new ApiSubError(code, field, message));
    }

    class ApiSubError {
        private String errorCode;
        private String field;
        private String message;

        public ApiSubError(String errorCode, String field, String message) {
            this.errorCode = errorCode;
            this.field = field;
            this.message = message;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
