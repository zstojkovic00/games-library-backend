package com.zeljko.gamelibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GameLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameLibraryApplication.class, args);

	}

}
