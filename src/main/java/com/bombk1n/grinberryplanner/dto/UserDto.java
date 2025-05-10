package com.bombk1n.grinberryplanner.dto;

import com.bombk1n.grinberryplanner.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private Set<UserRole> roles;
}
