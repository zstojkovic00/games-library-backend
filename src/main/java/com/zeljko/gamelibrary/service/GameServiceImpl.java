package com.zeljko.gamelibrary.service;


import com.zeljko.gamelibrary.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GameServiceImpl implements GameService {


    @Autowired
    private RestTemplate restTemplate;




    @Override
    public Game getGameById(Long userId) {
        Game result = restTemplate.getForObject("https://api.rawg.io/api/games/"+userId+"?key=bac66ee8265d4894b6534d314dcc726a", Game.class);
        System.out.println(result);
        return result;
    }
}
