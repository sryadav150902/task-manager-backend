package com.taskmanager.service;

import com.taskmanager.dto.RegisterRequestDTO;
import com.taskmanager.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(RegisterRequestDTO request);
}
