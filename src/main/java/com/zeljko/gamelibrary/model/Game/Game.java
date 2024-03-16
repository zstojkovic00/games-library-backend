package com.zeljko.gamelibrary.model.Game;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zeljko.gamelibrary.model.UserCredentials.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "games")
public class Game implements Serializable {

    @Id
    @Column(unique = true)
    private Long id;
    private String name;
    private String background_image;
    private Long playtime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "released")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date released;
    private float rating;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "games_genres_table",
            joinColumns = {
                    @JoinColumn(name = "game_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "genre_id", referencedColumnName = "id")
            })

    private Set<Genre> genres = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> userSet = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date addedAt;

}



