package com.bombk1n.grinberryplanner.dto;

import com.bombk1n.grinberryplanner.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for creating a new user")
public class UserCreateDto {

    @NotBlank
    @Size(min = 3, max = 30)
    @Schema(description = "Username of the new user", example = "john_doe")
    private String username;

    @NotBlank
    @Size(min = 5, max = 64)
    @Schema(description = "Password for the new user", example = "securePassword123")
    private String password;

    @Schema(description = "Set of roles assigned to the user", example = "[\"USER\"]")
    private Set<UserRole> roles;
}
