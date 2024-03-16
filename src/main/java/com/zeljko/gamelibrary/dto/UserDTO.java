package com.zeljko.gamelibrary.dto;


import com.zeljko.gamelibrary.model.Game.Game;
import com.zeljko.gamelibrary.model.UserCredentials.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String firstname;
    private String lastname;
    private String email;
    private Set<Game> games;
    @Enumerated(EnumType.STRING)
    private Role role;
}