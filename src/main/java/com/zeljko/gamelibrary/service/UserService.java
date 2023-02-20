package com.zeljko.gamelibrary.service;



import com.zeljko.gamelibrary.model.Game;
import com.zeljko.gamelibrary.model.User;
import com.zeljko.gamelibrary.repository.GameRepository;
import com.zeljko.gamelibrary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final GameService gameService;

    private final GameRepository gameRepository;



    public void deleteUserById(int id){
        userRepository.deleteById(id);

    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public User addGameToUser(Long gameId, Principal principal){
        Set<Game> gameSet = null;
        Game response = gameService.getGameById(gameId);
        gameRepository.save(response);
        Game game = gameRepository.findById(gameId).get();
        User user = userRepository.findByEmail(principal.getName()).get();
        game.setAddedAt(new Date());
        gameSet = user.getGames();
        gameSet.add(game);
        user.setGames(gameSet);

        return userRepository.save(user);
    }


    public User getCurrentUser(Principal principal) {

        User user = userRepository.findByEmail(principal.getName()).get();

        return user;
    }

    public List<Game> getCurrentUserGames(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();

        List <Game> games = user.getGames().stream().toList();

        return games;



    }
}
