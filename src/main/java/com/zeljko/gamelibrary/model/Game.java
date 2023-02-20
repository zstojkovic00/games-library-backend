package com.zeljko.gamelibrary.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name ="games")
public class Game {

    @Id
    @Column(unique = true)
    private Long id;
    private String name;
    private String background_image;
    private Long playtime;
    @JsonIgnore
    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> userSet = new HashSet<User>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added_at")
    private Date addedAt;





}
