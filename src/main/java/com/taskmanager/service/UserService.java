package com.taskmanager.service;

import com.taskmanager.dto.LoginRequestDTO;
import com.taskmanager.dto.RegisterRequestDTO;
import com.taskmanager.dto.UserResponseDTO;
import com.taskmanager.dto.AuthResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(RegisterRequestDTO request);
    AuthResponseDTO loginUser(LoginRequestDTO request);
}
