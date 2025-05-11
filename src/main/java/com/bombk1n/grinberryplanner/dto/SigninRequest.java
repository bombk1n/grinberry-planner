package com.bombk1n.grinberryplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object containing user credentials for login")
public class SigninRequest {

    @NotBlank(message = "Username is required")
    @Schema(description = "The username of the user trying to log in", example = "johnDoe")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "The password of the user trying to log in", example = "secretPassword123")
    private String password;
}
