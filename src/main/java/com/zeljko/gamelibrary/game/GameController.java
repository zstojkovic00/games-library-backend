package com.zeljko.gamelibrary.game;


import com.zeljko.gamelibrary.user.User;
import com.zeljko.gamelibrary.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;




    @GetMapping("/getGame/{gameId}")
    public Game SaveGameToRepositoryById(@PathVariable("gameId") Long gameId){
        Game response = gameService.getGameById(gameId);

        return gameRepository.save(response);

    }

    @GetMapping("/getAllGames")
    public List<Game> getAllGames(){

        return gameRepository.findAll();

    }

    @PutMapping("/getGame/{gameId}/user")
    User addGameToUser(
            @PathVariable("gameId") Long gameId,
            Principal principal
    ){
        Game response = gameService.getGameById(gameId);
        gameRepository.save(response);
        Game game = gameRepository.findById(gameId).get();
        User user = userRepository.findByEmail(principal.getName()).get();
        user.addGameToUser(game);

        return userRepository.save(user);

    }






}
