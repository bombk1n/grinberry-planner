package com.bombk1n.grinberryplanner.controller;

import com.bombk1n.grinberryplanner.dto.JwtAuthResponse;
import com.bombk1n.grinberryplanner.dto.SigninRequest;
import com.bombk1n.grinberryplanner.dto.SignupRequest;
import com.bombk1n.grinberryplanner.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@Valid @RequestBody SignupRequest request){
        System.out.println("Register request: " + request);
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody SigninRequest request){
        System.out.println("Login request: " + request);
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
