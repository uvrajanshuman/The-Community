package com.example.thecommunityapi.security;//package com.example.thecommunityapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.thecommunityapi.dto.*;
import com.example.thecommunityapi.model.Role;
import com.example.thecommunityapi.model.User;
import com.example.thecommunityapi.repository.RoleRepository;
import com.example.thecommunityapi.repository.UserRepository;
import com.example.thecommunityapi.security.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager; 
    @Autowired
    private final JWTUtil jwtUtil;

    //signup
    public SignupResponseDto saveUser(SignupRequestDto signupRequest){
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        Role role = roleRepository.findByRoleName("ROLE_USER");
        user.getRoles().add(role);
        userRepository.save(user);
        //returning the response
        return new SignupResponseDto(user.getUsername(),user.getEmail(),user.getRoles());

    }

    //login
    public AuthenticationResponseDto login(LoginRequestDto loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        System.out.println("Usernameaaa: "+username);
        System.out.println("Password: "+password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("Reached here");
        String access_token = jwtUtil.generateAcessToken(authentication);
        String refresh_token = jwtUtil.generateRefreshToken(authentication);

        //sending back the response with token.
        return new AuthenticationResponseDto(access_token,refresh_token,username);
    }

    //to get current logged in user
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        System.out.println("hi "+ SecurityContextHolder.getContext().getAuthentication());
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal);
    }

    //to check if a user is logged in or not
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }



    //not related to core spring security.
    public Role saveRole(Role role) {
        System.out.println("Saving role");
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        System.out.println("Adding role to user");
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(roleName);
        user.getRoles().add(role);
    }
    public User getUser(String username) {
        System.out.println("Fetching user: "+username);
        return userRepository.findByUsername(username);
    }

    public List<User> getUsers() {
        System.out.println("Fetching all the users");
        return userRepository.findAll();
    }

    public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest) {
        String refresh_token = refreshTokenRequest.getRefreshToken();
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refresh_token);
            String username = decodedJWT.getSubject();
            User user = userRepository.findByUsername(username);
            String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withClaim("roles", user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                    .sign(algorithm);
            return new AuthenticationResponseDto(access_token, refresh_token, username);
    }
}
