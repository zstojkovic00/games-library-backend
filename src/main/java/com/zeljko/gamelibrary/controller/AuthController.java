package com.zeljko.gamelibrary.controller;


import com.zeljko.gamelibrary.requests.AuthRequest;
import com.zeljko.gamelibrary.response.AuthResponse;
import com.zeljko.gamelibrary.requests.RegisterRequest;
import com.zeljko.gamelibrary.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        try {
            AuthResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var responseMap = createResponseMap("Invalid username or password");
            log.error(e.getMessage());
            return ResponseEntity.badRequest()
                    .body(responseMap);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        String refreshToken = authorizationHeader.replace("Bearer ", "");
        log.info(refreshToken);
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }


    private Map<String, String> createResponseMap(String message) {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", message);
        return responseMap;
    }

}
