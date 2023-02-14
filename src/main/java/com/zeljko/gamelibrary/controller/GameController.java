package com.zeljko.gamelibrary.controller;


import com.zeljko.gamelibrary.service.GameService;
import com.zeljko.gamelibrary.model.Game;
import com.zeljko.gamelibrary.repository.GameRepository;
import com.zeljko.gamelibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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










}
