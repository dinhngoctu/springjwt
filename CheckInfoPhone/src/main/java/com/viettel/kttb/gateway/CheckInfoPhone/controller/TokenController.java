package com.viettel.kttb.gateway.CheckInfoPhone.controller;

import com.viettel.kttb.gateway.CheckInfoPhone.config.JwtTokenProvider;
import com.viettel.kttb.gateway.CheckInfoPhone.domain.dto.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    private static final String BEARER = "Bearer ";

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUserName(),
                        user.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken( user.getUserName());
        String responseToken = BEARER + jwt;
        return new ResponseEntity<>(responseToken, HttpStatus.ACCEPTED);
    }
}
