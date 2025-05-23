package com.bombk1n.grinberryplanner.service;

import com.bombk1n.grinberryplanner.dto.JwtAuthResponse;
import com.bombk1n.grinberryplanner.dto.SigninRequest;
import com.bombk1n.grinberryplanner.dto.SignupRequest;
import com.bombk1n.grinberryplanner.entity.UserEntity;
import com.bombk1n.grinberryplanner.enums.UserRole;
import com.bombk1n.grinberryplanner.exceptions.UsernameAlreadyExistsException;
import com.bombk1n.grinberryplanner.repository.UserRepository;
import com.bombk1n.grinberryplanner.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public JwtAuthResponse register(SignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.error("Username already exists: {}", request.getUsername());
            throw new UsernameAlreadyExistsException("Username: " + request.getUsername() + " already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(UserRole.USER));
        userRepository.save(user);

        log.info("User registered successfully: {}", request.getUsername());
        return login(new SigninRequest(request.getUsername(), request.getPassword()));
    }

    public JwtAuthResponse login(SigninRequest request) {
        log.info("Attempting login for user: {}", request.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        log.info("User logged in successfully: {}", request.getUsername());
        return new JwtAuthResponse(jwtService.generateToken(request.getUsername()));
    }
}
