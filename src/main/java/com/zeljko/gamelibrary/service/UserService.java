package com.zeljko.gamelibrary.service;

import com.zeljko.gamelibrary.dto.UserDTO;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(int id);
    void deleteUserById(int id);
    UserDTO getCurrentUser(Authentication principal);
}
