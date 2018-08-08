package com.gmail.maksimus40a.test.stand.security.web;

import static com.gmail.maksimus40a.test.stand.security.service.HardcodeUserCredentials.HARDCODE_USER_NAME;
import static com.gmail.maksimus40a.test.stand.security.service.HardcodeUserCredentials.HARDCODE_USER_PASSWORD;

public abstract class AbstractHardcodeUserCredentialsEntity {

    AuthenticationRequest getHardcodeUserDetails() {
        return AuthenticationRequest.builder()
                .username(HARDCODE_USER_NAME.getCredential())
                .password(HARDCODE_USER_PASSWORD.getCredential())
                .build();
    }
}