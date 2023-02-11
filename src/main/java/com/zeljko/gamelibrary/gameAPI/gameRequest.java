package com.zeljko.gamelibrary.gameAPI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class gameRequest {


        private String photo;
        private String name;


}
