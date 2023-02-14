package com.zeljko.gamelibrary.controller;



import com.zeljko.gamelibrary.model.Game;
import com.zeljko.gamelibrary.repository.GameRepository;
import com.zeljko.gamelibrary.repository.UserRepository;
import com.zeljko.gamelibrary.requests.AuthenticationRequest;
import com.zeljko.gamelibrary.requests.AuthenticationResponse;
import com.zeljko.gamelibrary.service.AuthenticationService;
import com.zeljko.gamelibrary.requests.RegisterRequest;
import com.zeljko.gamelibrary.model.User;
import com.zeljko.gamelibrary.service.GameService;
import com.zeljko.gamelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final GameService gameService;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable("id") int id) {
        userService.deleteUserById(id);
    }


    @RequestMapping("/currentUserInfo")
    public Map<String, Object> currentUser(Principal principal) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("email",principal.getName());
        return model;
    }


//    @PutMapping("/getGame/{gameId}/user")
//    User addGameToUser(
//            @PathVariable("gameId") Long gameId,
//            Principal principal
//    ){
//      Game response = gameService.getGameById(gameId);
//       gameRepository.save(response);
//       Game game = gameRepository.findById(gameId).get();
//        User user = userRepository.findByEmail(principal.getName()).get();
//        user.addGameToUser(game);
//        return user;
//
//
//
//    }

        @PutMapping("/getGame/{gameId}/user")
    User addGameToUser(
            @PathVariable("gameId") Long gameId,
            Principal principal
    ){
            Set<Game> gameSet = null;
            Game response = gameService.getGameById(gameId);
            gameRepository.save(response);
            Game game = gameRepository.findById(gameId).get();
            User user = userRepository.findByEmail(principal.getName()).get();

            gameSet = user.getGames();
            gameSet.add(game);
            user.setGames(gameSet);

            return userRepository.save(user);


        }





}
