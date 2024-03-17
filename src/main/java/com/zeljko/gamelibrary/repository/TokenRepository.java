package com.zeljko.gamelibrary.repository;

import com.zeljko.gamelibrary.model.Token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = "SELECT t.* FROM token t INNER JOIN user u " +
            "ON t.user_id = u.id WHERE u.id = :id " +
            "AND (t.expired = false OR t.revoked = false)", nativeQuery = true)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);
}
