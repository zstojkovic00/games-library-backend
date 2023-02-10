package com.zeljko.gamelibrary.game;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game,Integer> {
    Optional<Game> findGameById(Integer id);
}
