package com.zeljko.gamelibrary.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeljko.gamelibrary.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class LogoutServiceImpl implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        String message = "You have been successfully logged out.";
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", message);

        try {
            PrintWriter writer = response.getWriter();
            objectMapper.writeValue(writer, responseMap);
            writer.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }
}
