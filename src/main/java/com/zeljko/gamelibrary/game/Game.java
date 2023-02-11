package com.zeljko.gamelibrary.game;


import com.zeljko.gamelibrary.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="user_games")
public class Game {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private String photo;
    private String name;


    @ManyToOne
    private User user;


}
