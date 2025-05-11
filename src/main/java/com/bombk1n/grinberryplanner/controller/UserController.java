package com.bombk1n.grinberryplanner.controller;

import com.bombk1n.grinberryplanner.dto.ErrorResponse;
import com.bombk1n.grinberryplanner.dto.UserDto;
import com.bombk1n.grinberryplanner.dto.UserUpdateDto;
import com.bombk1n.grinberryplanner.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Users", description = "Endpoints related to user profile")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get current user profile",
            description = "Returns profile information for the currently authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile fetched successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access. Please login to continue.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/profile")
    public UserDto getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Fetching profile for user: {}", userDetails.getUsername());
        return userService.getCurrentUser(userDetails);
    }

    @Operation(
            summary = "Update current user profile",
            description = "Updates the profile information for the currently authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class))),

                    @ApiResponse(responseCode = "400", description = "Invalid input data or username already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),

                    @ApiResponse(responseCode = "401", description = "Unauthorized access or bad credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),

                    @ApiResponse(responseCode = "404", description = "Related task not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PutMapping()
    public UserDto updateCurrentUser(@Valid @RequestBody UserUpdateDto userUpdateDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Updating profile for user: {}", userDetails.getUsername());
        return userService.updateCurrentUser(userDetails, userUpdateDto);
    }
}
