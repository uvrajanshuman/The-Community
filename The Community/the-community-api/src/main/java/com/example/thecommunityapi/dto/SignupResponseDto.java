package com.example.thecommunityapi.dto;

import com.example.thecommunityapi.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseDto {
    private String username;
    private String email;
    private Collection<Role> roles;
}
