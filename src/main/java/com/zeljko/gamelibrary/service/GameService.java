package com.zeljko.gamelibrary.service;

import com.zeljko.gamelibrary.model.Game.Game;
import com.zeljko.gamelibrary.model.Game.Games;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface GameService {

    Game getGameById(Long gameId);
    void addGameToUser(Long gameId, Authentication principal);
    void removeGameFromCurrentUser(Long gameId, Authentication principal);
    List<Game> getCurrentUserGames(Authentication principal);
    Games getAllGames(String pageSize, String criteria);
    Games getGamesBySlug(String gameSlug, Double grade);
}
