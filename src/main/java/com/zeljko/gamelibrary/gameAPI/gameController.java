package com.zeljko.gamelibrary.gameAPI;


import com.zeljko.gamelibrary.game.Game;
import com.zeljko.gamelibrary.game.GameRepository;
import com.zeljko.gamelibrary.user.User;
import com.zeljko.gamelibrary.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class gameController {

    private final GameService gameService;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @GetMapping("/findAll")
    List<Game> getGames(){
        return gameRepository.findAll();
    }

    @PostMapping("/create")
    Game createGame(@RequestBody gameRequest request){
        return gameService.createGame(request);
    }








}
