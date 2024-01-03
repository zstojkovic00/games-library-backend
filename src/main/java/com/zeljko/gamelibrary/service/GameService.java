package com.zeljko.gamelibrary.service;

import com.zeljko.gamelibrary.model.Game;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

public interface GameService {

    Game getGameById(Long gameId);
    void addGameToUser(Long gameId, Authentication principal);
    void removeGameFromCurrentUser(Long gameId, Authentication principal);
    List<Game> getCurrentUserGames(Authentication principal);
    List<Game> getAllGames();
}
