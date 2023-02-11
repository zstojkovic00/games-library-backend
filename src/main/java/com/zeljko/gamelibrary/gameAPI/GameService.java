package com.zeljko.gamelibrary.gameAPI;

import com.zeljko.gamelibrary.game.Game;
import com.zeljko.gamelibrary.game.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;


    public Game createGame(gameRequest request) {
        var game = Game.builder().photo(request.getPhoto()).name(request.getName()).build();
        gameRepository.save(game);

        return game;



    }
}
