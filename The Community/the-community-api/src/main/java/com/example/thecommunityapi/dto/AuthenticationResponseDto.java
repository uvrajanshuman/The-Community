package com.example.thecommunityapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class AuthenticationResponseDto {
    private String acessToken;
    private String refreshToken;
    private String username;
}
