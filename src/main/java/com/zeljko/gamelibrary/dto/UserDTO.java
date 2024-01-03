package com.zeljko.gamelibrary.dto;


import com.zeljko.gamelibrary.model.Game;
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
}