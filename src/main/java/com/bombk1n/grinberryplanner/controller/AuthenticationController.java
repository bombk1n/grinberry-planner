package com.bombk1n.grinberryplanner.controller;

import com.bombk1n.grinberryplanner.dto.ErrorResponse;
import com.bombk1n.grinberryplanner.dto.JwtAuthResponse;
import com.bombk1n.grinberryplanner.dto.SigninRequest;
import com.bombk1n.grinberryplanner.dto.SignupRequest;
import com.bombk1n.grinberryplanner.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "Register new user",
            description = "Creates a new user account and returns a JWT token.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = SignupRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtAuthResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input or username already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@Valid @RequestBody SignupRequest request) {
        log.info("Received register request: {}", request);
        JwtAuthResponse response = authenticationService.register(request);
        log.info("Register response: {}", response);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Login user",
            description = "Authenticates user credentials and returns a JWT token.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = SigninRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged in successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtAuthResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid username or password",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody SigninRequest request) {
        log.info("Received login request: {}", request);
        JwtAuthResponse response = authenticationService.login(request);
        log.info("Login response: {}", response);
        return ResponseEntity.ok(response);
    }
}