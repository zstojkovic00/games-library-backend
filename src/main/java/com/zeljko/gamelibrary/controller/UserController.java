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
import java.util.List;

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
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable("id") int id) {
        userService.deleteUserById(id);
    }


    @PutMapping("/getGame/{gameId}/user")
    User addGameToUser(
            @PathVariable("gameId") Long gameId,
            Principal principal
    ) {

        return userService.addGameToUser(gameId, principal);

    }

    @DeleteMapping("/deleteGame/{gameId}/user")
    User removeGameFromCurrentUser(
            @PathVariable("gameId") Long gameId,
            Principal principal
    ) {

        return userService.removeGameFromCurrentUser(gameId, principal);

    }

    @GetMapping("/getCurrentUser")
    User getCurrentUser(Principal principal){
        return  userService.getCurrentUser(principal);
    }


    @GetMapping("/getCurrentUserGames")
    public List<Game> getCurrentUserGames(Principal principal){

      return userService.getCurrentUserGames(principal);

    }




}
