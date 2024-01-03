package com.zeljko.gamelibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

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
@Table(name = "genres")
public class Genre {

    @Id
    private long id;
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Game> gameSet = new HashSet<>();
}
