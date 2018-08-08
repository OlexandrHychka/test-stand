package com.gmail.maksimus40a.test.stand.security.repository;

public class SuchUserIsPresentException extends RuntimeException {
    public SuchUserIsPresentException(String message) {
        super(message);
    }
}