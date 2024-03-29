package com.zeljko.gamelibrary.service.impl;

import com.zeljko.gamelibrary.model.Token.Token;
import com.zeljko.gamelibrary.model.Token.TokenType;
import com.zeljko.gamelibrary.repository.TokenRepository;
import com.zeljko.gamelibrary.requests.AuthRequest;
import com.zeljko.gamelibrary.requests.RefreshTokenRequest;
import com.zeljko.gamelibrary.response.AuthResponse;
import com.zeljko.gamelibrary.requests.RegisterRequest;
import com.zeljko.gamelibrary.model.UserCredentials.Role;
import com.zeljko.gamelibrary.model.UserCredentials.User;
import com.zeljko.gamelibrary.repository.UserRepository;
import com.zeljko.gamelibrary.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Value("${jwt.access-token-expiration}")
    private long jwtAccessTokenExpiration;
    LocalDateTime expirationDate = LocalDateTime.now()
            .plus(jwtAccessTokenExpiration, ChronoUnit.MILLIS);

    @Override
    public AuthResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);


        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .expirationDate(expirationDate)
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

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        AuthResponse response = AuthResponse.
                builder().
                accessToken(jwtToken)
                .refreshToken(refreshToken)
                .expirationDate(expirationDate)
                .build();
        log.info("Authenticate {}", response.toString());
        return response;

    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        String newAccessToken = null;
        String newRefreshToken = null;

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token is missing in the request");
        }

        String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            User user = repository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!jwtService.isTokenValid(refreshToken, user)) {
                throw new RuntimeException("Refresh token is invalid");
            }

            revokeAllUserTokens(user);
            newAccessToken = jwtService.generateAccessToken(new HashMap<>(), user);
            saveUserToken(user, newAccessToken);
            newRefreshToken = jwtService.generateRefreshToken(user);
        }

        AuthResponse response = AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expirationDate(expirationDate)
                .build();
        log.info("Refresh token {}", response.toString());
        return response;
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


}

