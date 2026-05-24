package com.taskmanager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanager.dto.AuthResponseDTO;
import com.taskmanager.dto.LoginRequestDTO;
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
    private final JwtService jwtService;

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

    @Override
    public AuthResponseDTO loginUser(LoginRequestDTO request){

            User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(()-> new RuntimeException("Invalid Email or Password"));

            boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());

            if(!isPasswordMatch){
                throw new RuntimeException("Invalid password");
            }

            String token = jwtService.generateToken(user.getEmail());

            return AuthResponseDTO.builder()
            .token(token)
            .message("Login Successful")
            .build();
    }
}
