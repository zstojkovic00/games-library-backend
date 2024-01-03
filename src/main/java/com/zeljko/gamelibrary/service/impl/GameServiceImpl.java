package com.zeljko.gamelibrary.service.impl;


import com.zeljko.gamelibrary.model.Game;
import com.zeljko.gamelibrary.model.User;
import com.zeljko.gamelibrary.repository.GameRepository;
import com.zeljko.gamelibrary.repository.UserRepository;
import com.zeljko.gamelibrary.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final RestTemplate restTemplate;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game getGameById(Long userId) {
        return restTemplate.getForObject("https://api.rawg.io/api/games/" + userId + "?key=bac66ee8265d4894b6534d314dcc726a", Game.class);
    }

    @Override
    public List<Game> getCurrentUserGames(Authentication principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        return user.getGames().stream().toList();
    }


    @Override
    public void addGameToUser(Long gameId, Authentication principal) {
        Set<Game> gameSet;
        Game response = getGameById(gameId);
        gameRepository.save(response);
        Game game = gameRepository.findById(gameId).get();
        User user = userRepository.findByEmail(principal.getName()).get();
        game.setAddedAt(new Date());
        gameSet = user.getGames();
        gameSet.add(game);
        user.setGames(gameSet);
        userRepository.save(user);
    }

    @Override
    public void removeGameFromCurrentUser(Long gameId, Authentication principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        Set<Game> gameSet = user.getGames();
        Game gameToRemove = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        gameSet.remove(gameToRemove);
        user.setGames(gameSet);
        userRepository.save(user);
    }
}
