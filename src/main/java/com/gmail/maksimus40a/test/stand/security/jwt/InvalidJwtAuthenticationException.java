package com.gmail.maksimus40a.test.stand.security.jwt;

public class InvalidJwtAuthenticationException extends RuntimeException {

    public InvalidJwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}