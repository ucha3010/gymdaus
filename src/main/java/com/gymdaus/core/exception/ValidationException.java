package com.gymdaus.core.exception;

public class ValidationException extends GeneralException {

    public ValidationException(String adviceCode, String message) {
        super(adviceCode, message);
    }
}
