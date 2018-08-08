package com.gmail.maksimus40a.test.stand.security.service;

public enum HardcodeUserCredentials {

    HARDCODE_USER_NAME("user"),
    HARDCODE_USER_PASSWORD("password");

    private String credential;

    HardcodeUserCredentials(String credential) {
        this.credential = credential;
    }

    public String getCredential() {
        return this.credential;
    }
}