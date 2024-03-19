package com.zeljko.gamelibrary;

import com.zeljko.gamelibrary.model.UserCredentials.Role;
import com.zeljko.gamelibrary.requests.RegisterRequest;
import com.zeljko.gamelibrary.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
@Slf4j
public class GameLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameLibraryApplication.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthService authService
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Zeljko")
                    .lastname("Stojkovic")
                    .email("00zeljkostojkovic@gmail.com")
                    .password("sifra1234")
                    .role(Role.ADMIN)
                    .build();

            log.info("Admin token: {}", authService.register(admin).getAccessToken());

            var user = RegisterRequest.builder()
                    .firstname("Zeljko")
                    .lastname("Stojkovic")
                    .email("zeljko@gmail.com")
                    .password("sifra1234")
                    .role(Role.USER)
                    .build();
            log.info("User token: {}", authService.register(user).getAccessToken());
        };
    }
}
