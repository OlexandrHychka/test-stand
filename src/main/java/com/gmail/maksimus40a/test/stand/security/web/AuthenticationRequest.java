package com.gmail.maksimus40a.test.stand.security.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {
    private String username;
    private String password;
}