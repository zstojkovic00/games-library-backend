package com.zeljko.gamelibrary.model.Game;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Games {
    private int count;
    private String next;
    private String previous;
    private List<Game> results;
}
