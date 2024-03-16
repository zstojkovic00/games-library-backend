package com.zeljko.gamelibrary.repository;

import com.zeljko.gamelibrary.model.Game.Game;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameRepository extends JpaRepository<Game, Long> {

}
