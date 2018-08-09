package com.gmail.maksimus40a.test.stand.security.web;

import com.gmail.maksimus40a.test.stand.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static java.util.Objects.isNull;

@RestController
public class AuthController extends AbstractHardcodeUserCredentialsEntity {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/bookstore/login")
    public ResponseEntity signInBookstore(@RequestBody(required = false) AuthenticationRequest data) {
        return signIn(data);
    }

    @PostMapping("/api/itcompany/login")
    public ResponseEntity signInItCompany(@RequestBody(required = false) AuthenticationRequest data) {
        return signIn(data);
    }

    private ResponseEntity signIn(AuthenticationRequest data) {
        if (isNull(data)) data = getHardcodeUserDetails();
        String token = "Bearer " + authService.authenticateAndGetToken(data);
        HashMap<Object, Object> model = new HashMap<>();
        model.put("token", token);
        return ResponseEntity.ok(model);
    }
}