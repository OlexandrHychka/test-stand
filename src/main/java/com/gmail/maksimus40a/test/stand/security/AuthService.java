package com.gmail.maksimus40a.test.stand.security;

import com.gmail.maksimus40a.test.stand.security.jwt.JwtTokenProvider;
import com.gmail.maksimus40a.test.stand.security.repository.UserRepository;
import com.gmail.maksimus40a.test.stand.security.web.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;
    private UserRepository userRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider tokenProvider,
                       @Qualifier("list") UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    public String authenticateAndGetToken(AuthenticationRequest data) {
        try {
            String userName = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, data.getPassword()));
            return tokenProvider.createToken(userName, this.userRepository.findByUserName(userName).orElseThrow(
                    () -> new UsernameNotFoundException("Username " + userName + " not found.")).getRoles());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
