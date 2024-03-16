package com.zeljko.gamelibrary.service.impl;

import com.zeljko.gamelibrary.requests.AuthRequest;
import com.zeljko.gamelibrary.requests.RefreshTokenRequest;
import com.zeljko.gamelibrary.response.AuthResponse;
import com.zeljko.gamelibrary.requests.RegisterRequest;
import com.zeljko.gamelibrary.model.UserCredentials.Role;
import com.zeljko.gamelibrary.model.UserCredentials.User;
import com.zeljko.gamelibrary.repository.UserRepository;
import com.zeljko.gamelibrary.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.
                builder().
                accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String newAccessToken = null;
        String newRefreshToken = null;

        if (request == null || request.refreshToken().isEmpty()) {
            throw new IllegalArgumentException("Refresh token is missing in the request");
        }

        String userEmail = jwtService.extractUsername(request.refreshToken());

        if (userEmail != null) {
            User user = repository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!jwtService.isTokenValid(request.refreshToken(), user)) {
                throw new RuntimeException("Refresh token is invalid");
            }

            newAccessToken = jwtService.generateAccessToken(new HashMap<>(), user);
            newRefreshToken = jwtService.generateRefreshToken(user);
        }

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }


}

