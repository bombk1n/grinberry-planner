package com.bombk1n.grinberryplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for updating a user")
public class UserUpdateDto {

    @Size(min = 3, max = 30)
    @Schema(description = "New username of the user", example = "john_doe")
    private String username;

    @Schema(description = "Old password for the user", example = "securePassword123")
    private String OldPassword;


    @Size(min = 5, max = 64)
    @Schema(description = "New password for the user", example = "securePassword321")
    private String newPassword;
}
