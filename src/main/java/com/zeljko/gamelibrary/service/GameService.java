package com.zeljko.gamelibrary.service;

import com.zeljko.gamelibrary.model.Game;

import java.security.Principal;
import java.util.List;

public interface GameService {

    Game getGameById(Long gameId);
    void addGameToUser(Long gameId, Principal principal);
    void removeGameFromCurrentUser(Long gameId, Principal principal);
    List<Game> getCurrentUserGames(Principal principal);
    List<Game> getAllGames();
}
