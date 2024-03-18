package com.zeljko.gamelibrary.model.Game;


import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Games implements Serializable {
    private int count;
    private String next;
    private String previous;
    private List<Game> results;
}
