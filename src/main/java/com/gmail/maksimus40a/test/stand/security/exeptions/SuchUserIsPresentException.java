package com.gmail.maksimus40a.test.stand.security.exeptions;

public class SuchUserIsPresentException extends RuntimeException {
    public SuchUserIsPresentException(String message) {
        super(message);
    }
}