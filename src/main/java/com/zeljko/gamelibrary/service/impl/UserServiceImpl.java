package com.zeljko.gamelibrary.service.impl;


import com.zeljko.gamelibrary.dto.UserDTO;
import com.zeljko.gamelibrary.exception.custom.UserNotFoundException;
import com.zeljko.gamelibrary.model.UserCredentials.User;
import com.zeljko.gamelibrary.repository.UserRepository;
import com.zeljko.gamelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserServiceImpl::convertUserToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(int id) {
        return userRepository.findById(id)
                .map(UserServiceImpl::convertUserToDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getCurrentUser(Authentication principal) {
        return userRepository.findByEmail(principal.getName())
                .map(UserServiceImpl::convertUserToDTO)
                .orElseThrow(() -> new UserNotFoundException("Current user not found"));
    }

    public static UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setGames(user.getGames());
        return userDTO;
    }
}
