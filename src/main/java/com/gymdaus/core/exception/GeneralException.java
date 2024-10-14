package com.gymdaus.core.exception;

public class GeneralException extends Exception {

    protected final String adviceCode;
    protected final String message;

    public GeneralException(String adviceCode, String message) {
        this.adviceCode = adviceCode;
        this.message = message;
    }

    public String getAdviceCode() {
        return adviceCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "GeneralException{" +
                "adviceCode='" + adviceCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
