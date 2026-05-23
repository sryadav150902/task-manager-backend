package com.taskmanager.dto;

import lombok.*;
import com.taskmanager.entity.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

        private Long id;
        private String name;
        private String email;
        private Role role;
        private String organizationName;
}
