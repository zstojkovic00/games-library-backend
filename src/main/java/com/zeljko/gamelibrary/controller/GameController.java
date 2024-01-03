package com.zeljko.gamelibrary.controller;


import com.zeljko.gamelibrary.model.Game;
import com.zeljko.gamelibrary.repository.GameRepository;
import com.zeljko.gamelibrary.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameRepository gameRepository;

    @GetMapping("/all")
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{gameId}")
    public Game SaveGameToRepositoryById(@PathVariable("gameId") Long gameId) {
        Game response = gameService.getGameById(gameId);
        return gameRepository.save(response);
    }

    @GetMapping("/all/current-user")
    public List<Game> getCurrentUserGames(Principal principal) {
        return gameService.getCurrentUserGames(principal);
    }

    @PutMapping("/{gameId}/current-user")
    ResponseEntity<String> addGameToUser(@PathVariable("gameId") Long gameId, Principal principal) {
        gameService.addGameToUser(gameId, principal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Game is successfully added.");
    }

    @DeleteMapping("/{gameId}/current-user")
    ResponseEntity<String> removeGameFromCurrentUser(@PathVariable("gameId") Long gameId, Principal principal) {
        gameService.removeGameFromCurrentUser(gameId, principal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Game is successfully deleted.");
    }

}
