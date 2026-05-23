package com.taskmanager.dto;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequestDTO {

        @NotBlank(message="Name is required")
        private String name;

        @Email
        @NotBlank(message  = "Email is required")
        private String email;

        @NotBlank(message = "Password is reuired")
        @Size(min=6)
        private String password;

        @NotBlank(message = "Organization name is required")
        private String organizationName;
}
