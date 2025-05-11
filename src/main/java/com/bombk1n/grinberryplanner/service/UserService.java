package com.bombk1n.grinberryplanner.service;

import com.bombk1n.grinberryplanner.dto.UserDto;
import com.bombk1n.grinberryplanner.entity.UserEntity;
import com.bombk1n.grinberryplanner.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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
}
