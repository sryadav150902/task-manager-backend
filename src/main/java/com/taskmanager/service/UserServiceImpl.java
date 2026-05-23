package com.taskmanager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanager.dto.RegisterRequestDTO;
import com.taskmanager.dto.UserResponseDTO;
import com.taskmanager.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.taskmanager.entity.Role;
import com.taskmanager.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO registerUser(RegisterRequestDTO request){

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if(existingUser.isPresent()){
            throw new RuntimeException("Email already exists");
        }
       
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(hashedPassword)
                .organizationName(request.getOrganizationName())
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        UserResponseDTO response = UserResponseDTO.builder()
        .id(savedUser.getId())
        .name(savedUser.getName())
        .email(savedUser.getEmail())
        .role(savedUser.getRole())
        .organizationName(savedUser.getOrganizationName())
        .build();

        return response;
    }
}
