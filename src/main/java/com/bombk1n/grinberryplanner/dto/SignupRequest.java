package com.bombk1n.grinberryplanner.dto;

import com.bombk1n.grinberryplanner.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

    private Set<UserRole> roles;

    public @NotBlank(message = "Username is required") String getUsername() {
        return username;
    }


}