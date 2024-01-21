package com.zeljko.gamelibrary.controller;


import com.zeljko.gamelibrary.model.Game;
import com.zeljko.gamelibrary.repository.GameRepository;
import com.zeljko.gamelibrary.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameRepository gameRepository;

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGameById(@PathVariable("gameId") Long gameId) {
        Game response = gameService.getGameById(gameId);
        return ResponseEntity.ok(gameRepository.save(response));
    }

    @GetMapping("/slug/{gameSlug}")
    public ResponseEntity<List<Game>> getGamesBySlug(@PathVariable("gameSlug") String gameSlug){
        List<Game> gameListResponse = gameService.getGamesBySlug(gameSlug);
        return ResponseEntity.ok(gameListResponse);
    }

    @GetMapping("/current-user")
    public List<Game> getCurrentUserGames(Authentication principal) {
        return gameService.getCurrentUserGames(principal);
    }

    @PatchMapping("/{gameId}/current-user")
    ResponseEntity<String> addGameToUser(@PathVariable("gameId") Long gameId, Authentication principal) {
        gameService.addGameToUser(gameId, principal);
        return ResponseEntity.status(HttpStatus.OK).body("Game is successfully added.");
    }

    @DeleteMapping("/{gameId}/current-user")
    ResponseEntity<String> removeGameFromCurrentUser(@PathVariable("gameId") Long gameId, Authentication principal) {
        gameService.removeGameFromCurrentUser(gameId, principal);
        return ResponseEntity.status(HttpStatus.OK).body("Game is successfully deleted.");
    }

}
