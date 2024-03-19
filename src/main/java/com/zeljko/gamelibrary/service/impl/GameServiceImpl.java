package com.zeljko.gamelibrary.service.impl;


import com.zeljko.gamelibrary.model.Game.Game;
import com.zeljko.gamelibrary.model.Game.Games;
import com.zeljko.gamelibrary.model.UserCredentials.User;
import com.zeljko.gamelibrary.repository.GameRepository;
import com.zeljko.gamelibrary.repository.UserRepository;
import com.zeljko.gamelibrary.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final RestClient restClient;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Value("${rawg.api.key}")
    private String rawgApiKey;
    @Value("${rawg.api.base}")
    public String rawgApiBase;

    @Override
    @Cacheable(value = "gamesCache", key = "#pageSize + '_' + #criteria")
    public Games getAllGames(String pageSize, String criteria) {
        String query = createQuery(pageSize, criteria);

        log.info(query);

        String uriString = rawgApiBase + "?key=" + rawgApiKey + query;
        log.info("Request URI: {}", uriString);

        Games games = restClient.get()
                .uri(uriString)
                .retrieve()
                .body(Games.class);
        log.info("Games: " + games);
        return games;

    }

    @Override
    public Game getGameById(Long gameId) {

        String uriString = rawgApiBase + "/" + gameId + "?key=" + rawgApiKey;
        log.info("Request URI: {}", uriString);

        return restClient.get()
                .uri(uriString)
                .retrieve()
                .body(Game.class);


    }

    @Override
    public Games getGamesBySlug(String gameSlug, Double rating) {
        String uriString = rawgApiBase + "?search=" + gameSlug + "&key=" + rawgApiKey;
        log.info("Request URI: {}", uriString);

        var games = restClient.get().uri(uriString)
                .retrieve()
                .body(Games.class);

        if (games != null && games.getResults() != null) {
            List<Game> filteredGames = games.getResults()
                    .stream()
                    .filter(game -> game.getRating() > rating)
                    .toList();
            games.setResults(filteredGames);
        }

        return games;
    }

    @Override
    public List<Game> getCurrentUserGames(Authentication principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        return user.getGames().stream().toList();
    }

    @Override
    public void addGameToUser(Long gameId, Authentication principal) {
        Optional.ofNullable(getGameById(gameId))
                .ifPresent(gameRepository::save);

        Game newGame = gameRepository.findById(gameId).get();
        User currentUser = userRepository.findByEmail(principal.getName()).get();

        newGame.setAddedAt(new Date());
        currentUser.getGames().add(newGame);
        userRepository.save(currentUser);
    }

    @Override
    public void removeGameFromCurrentUser(Long gameId, Authentication principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        Game gameToRemove = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));

        user.getGames().remove(gameToRemove);
        userRepository.save(user);
    }

    private static String createQuery(String pageSize, String criteria) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime nextYear = LocalDateTime.now().plusYears(1);
        LocalDateTime lastYear = LocalDateTime.now().minusYears(1);

        String currentDate = currentTime.format(FORMATTER);
        String lastYearDate = lastYear.format(FORMATTER);
        String nextYearDate = nextYear.format(FORMATTER);

        String query = criteria;

        switch (criteria) {
            case "popular-games" ->
                    query = "&dates=" + lastYearDate + "," + currentDate + "&ordering=-rating&&page_size=" + pageSize;
            case "upcoming-games" ->
                    query = "&dates=" + currentDate + "," + nextYearDate + "&ordering=-added&&page_size=" + pageSize;
            case "new-games" ->
                    query = "&dates=" + lastYearDate + "," + currentDate + "&ordering=-released&&page_size=" + pageSize;

            case "best-games" -> query = "&&page_size=" + pageSize;
        }
        return query;
    }
}
