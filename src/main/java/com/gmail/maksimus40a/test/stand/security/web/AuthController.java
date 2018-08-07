package com.gmail.maksimus40a.test.stand.security.web;

import com.gmail.maksimus40a.test.stand.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static java.util.Objects.*;

@RestController
@RequestMapping("/api/bookstore")
public class AuthController {

    private final String HARDCODED_USERNAME = "user";
    private final String HARDCODED_PASSWORD = "password";

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity signin(@RequestBody(required = false) AuthenticationRequest data) {
        if (isNull(data)) {
            data = AuthenticationRequest.builder()
                    .username(HARDCODED_USERNAME)
                    .password(HARDCODED_PASSWORD)
                    .build();
        }
        String token = "Bearer " + authService.authenticateAndGetToken(data);
        HashMap<Object, Object> model = new HashMap<>();
        model.put("token", token);
        return ResponseEntity.ok(model);
    }
}