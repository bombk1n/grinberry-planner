package com.bombk1n.grinberryplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for user registration, containing user credentials and roles")
public class SignupRequest {

    @NotBlank(message = "Username is required")
    @Schema(description = "The username for the new user", example = "johnDoe123")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    @Schema(description = "The password for the new user. It must be at least 5 characters long", example = "secret123")
    private String password;

}