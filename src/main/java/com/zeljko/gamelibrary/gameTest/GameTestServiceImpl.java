package com.zeljko.gamelibrary.gameTest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GameTestServiceImpl implements GameTestService {

    @Value("${https://api.rawg.io/api/games}")
    private String gameApiBaseUrl;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public GameModal getAllGames() {
        GameModal result = restTemplate.getForObject("https://api.rawg.io/api/games/3498?key=bac66ee8265d4894b6534d314dcc726a", GameModal.class);
        System.out.println(result);
        return result;
    }
}
