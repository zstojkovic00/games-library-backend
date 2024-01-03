package com.zeljko.gamelibrary.service;

import com.zeljko.gamelibrary.requests.AuthRequest;
import com.zeljko.gamelibrary.requests.AuthResponse;
import com.zeljko.gamelibrary.requests.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);
}
