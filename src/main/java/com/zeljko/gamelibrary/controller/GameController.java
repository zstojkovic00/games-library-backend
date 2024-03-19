package com.zeljko.gamelibrary.controller;


import com.zeljko.gamelibrary.model.Game.Game;
import com.zeljko.gamelibrary.model.Game.Games;
import com.zeljko.gamelibrary.repository.GameRepository;
import com.zeljko.gamelibrary.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Games> getAllGames(@RequestParam(name = "pageSize", defaultValue = "40") String pageSize,
                                             @RequestParam(name = "criteria", defaultValue = "best-games") String criteria) {
        Games gameListResponse = gameService.getAllGames(pageSize, criteria);
        return ResponseEntity.ok(gameListResponse);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGameById(@PathVariable("gameId") Long gameId) {
        Game response = gameService.getGameById(gameId);
        return ResponseEntity.ok(gameRepository.save(response));
    }

    @GetMapping("/slug/{gameSlug}")
    public ResponseEntity<Games> getGamesBySlug(
            @PathVariable("gameSlug") String gameSlug,
            @RequestParam(name = "rating", defaultValue = "0") double grade
    ) {
        Games gameListResponse = gameService.getGamesBySlug(gameSlug, grade);
        return ResponseEntity.ok(gameListResponse);
    }

    @GetMapping("/current-user")
    @Cacheable(value = "games", key = "#principal")
    public List<Game> getCurrentUserGames(Authentication principal) {
        return gameService.getCurrentUserGames(principal);
    }

    @PatchMapping("/{gameId}/current-user")
    ResponseEntity<String> addGameToUser(@PathVariable("gameId") Long gameId, Authentication principal) {
        gameService.addGameToUser(gameId, principal);
        return ResponseEntity.status(HttpStatus.OK).body("Game is successfully added.");
    }

    @DeleteMapping("/{gameId}/current-user")
    @PreAuthorize("hasAuthority('admin:delete')")
    ResponseEntity<String> removeGameFromCurrentUser(@PathVariable("gameId") Long gameId, Authentication principal) {
        gameService.removeGameFromCurrentUser(gameId, principal);
        return ResponseEntity.status(HttpStatus.OK).body("Game is successfully deleted.");
    }

}
