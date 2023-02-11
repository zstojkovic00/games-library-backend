package com.zeljko.gamelibrary.auth;


import com.zeljko.gamelibrary.game.Game;
import com.zeljko.gamelibrary.game.GameRepository;
import com.zeljko.gamelibrary.user.User;
import com.zeljko.gamelibrary.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }


    @GetMapping("/users")
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id){
        return service.getUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable("id") int id) {
        service.deleteUserById(id);
    }


    @RequestMapping("/currentUserInfo")
    public Map<String, Object> currentUser(Principal principal) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("email",principal.getName());
        return model;
    }


    @PutMapping("/{gameId}/users/{userId}")
    User addGameToUser(
            @PathVariable int gameId,
            @PathVariable int userId
    ){
        User user = userRepository.findById(userId).get();
        Game game = gameRepository.findById(gameId).get();
        user.addGameToUser(game);

        return userRepository.save(user);
    }




}
