package ru.tinkoff.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LandscapeSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(LandscapeSpringApplication.class, args);
    }
}
