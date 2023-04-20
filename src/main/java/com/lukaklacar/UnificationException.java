package com.lukaklacar;

public class UnificationException extends RuntimeException {
    public UnificationException() {
    }

    public UnificationException(String message) {
        super(message);
    }

    public UnificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnificationException(Throwable cause) {
        super(cause);
    }

    public UnificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
