package com.bombk1n.grinberryplanner.controller;

import com.bombk1n.grinberryplanner.dto.JwtAuthResponse;
import com.bombk1n.grinberryplanner.dto.SigninRequest;
import com.bombk1n.grinberryplanner.dto.SignupRequest;
import com.bombk1n.grinberryplanner.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@Valid @RequestBody SignupRequest request) {
        log.info("Received register request: {}", request);
        JwtAuthResponse response = authenticationService.register(request);
        log.info("Register response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody SigninRequest request) {
        log.info("Received login request: {}", request);
        JwtAuthResponse response = authenticationService.login(request);
        log.info("Login response: {}", response);
        return ResponseEntity.ok(response);
    }
}
