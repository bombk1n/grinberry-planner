package com.bombk1n.grinberryplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Response containing JWT token for user authentication")
public class JwtAuthResponse {

    @Schema(description = "JWT token generated after successful login or registration", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6ImpvaG5AZ21haWwuY29tIiwiaWF0IjoxNjYyODU4NzYxfQ.XYhZ5UBvw5dXGd0jJGGOMs8XmtfWnlVkA19ZjN4mfhE")
    private String token;
}
