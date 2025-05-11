package com.bombk1n.grinberryplanner.service;

import com.bombk1n.grinberryplanner.dto.UserDto;
import com.bombk1n.grinberryplanner.dto.UserUpdateDto;
import com.bombk1n.grinberryplanner.entity.UserEntity;
import com.bombk1n.grinberryplanner.exceptions.UsernameAlreadyExistsException;
import com.bombk1n.grinberryplanner.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto getCurrentUser(UserDetails userDetails) {
        log.info("Fetching current user details for username: {}", userDetails.getUsername());

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", userDetails.getUsername());
                    return new UsernameNotFoundException("User not found");
                });

        log.info("Successfully retrieved user details for username: {}", userDetails.getUsername());
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    public UserDto updateCurrentUser(UserDetails userDetails, UserUpdateDto userUpdateDto) {
        UserEntity currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", userDetails.getUsername());
                    return new UsernameNotFoundException("User not found");
                });

        String newUsername = userUpdateDto.getUsername();
        if (newUsername != null && !newUsername.equals(currentUser.getUsername())) {
            if (userRepository.findByUsername(newUsername).isPresent()) {
                log.error("Username already exists: {}", newUsername);
                throw new UsernameAlreadyExistsException("Username: " + newUsername + " already exists");
            }
            currentUser.setUsername(newUsername);
        }

        String newPassword = userUpdateDto.getNewPassword();
        if (newPassword != null && !newPassword.isBlank()) {
            if (userUpdateDto.getOldPassword() == null ||
                    !passwordEncoder.matches(userUpdateDto.getOldPassword(), currentUser.getPassword())) {
                log.error("Old password does not match for user: {}", currentUser.getUsername());
                throw new BadCredentialsException("Old password does not match");
            }
            currentUser.setPassword(passwordEncoder.encode(newPassword));
        }

        if ((newUsername.equals(currentUser.getUsername())) &&
                (newPassword == null || newPassword.isBlank())) {
            log.info("No changes submitted for user: {}", currentUser.getUsername());
            return modelMapper.map(currentUser, UserDto.class);
        }

        UserEntity updatedUser = userRepository.save(currentUser);
        log.info("Successfully updated user details for username: {}", updatedUser.getUsername());

        return modelMapper.map(updatedUser, UserDto.class);
    }
}
