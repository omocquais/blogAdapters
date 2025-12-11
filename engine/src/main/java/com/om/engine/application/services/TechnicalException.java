package com.om.engine.application.services;

public class TechnicalException extends RuntimeException {
    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
