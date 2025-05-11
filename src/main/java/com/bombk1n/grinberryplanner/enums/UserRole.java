package com.bombk1n.grinberryplanner.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Enumeration of possible user roles")
public enum UserRole {

    @Schema(description = "Standard user with limited access")
    USER,

    @Schema(description = "Administrator with full access")
    ADMIN
}
