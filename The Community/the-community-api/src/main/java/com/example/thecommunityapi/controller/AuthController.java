package com.example.thecommunityapi.controller;

import com.example.thecommunityapi.dto.*;
import com.example.thecommunityapi.model.Role;
import com.example.thecommunityapi.model.User;
import com.example.thecommunityapi.security.AuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    //signup
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequest){
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.saveUser(signupRequest));
    }

    //login
    @PostMapping("/login")
    public AuthenticationResponseDto login(@RequestBody LoginRequestDto loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hi";
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(authService.getUsers());
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
        authService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponseDto refreshTokens(@RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}

