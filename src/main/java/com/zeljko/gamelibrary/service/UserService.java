package com.zeljko.gamelibrary.service;

import com.zeljko.gamelibrary.model.User;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(int id);
    void deleteUserById(int id);
    User getCurrentUser(Authentication principal);
}
