package com.zeljko.gamelibrary.service.impl;


import com.zeljko.gamelibrary.model.User;
import com.zeljko.gamelibrary.repository.UserRepository;
import com.zeljko.gamelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getCurrentUser(Authentication principal) {
        return userRepository.findByEmail(principal.getName()).get();
    }

}
