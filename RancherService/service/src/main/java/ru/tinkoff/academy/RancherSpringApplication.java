package ru.tinkoff.academy;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableMongock
@SpringBootApplication
@EnableScheduling
public class RancherSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(RancherSpringApplication.class, args);
    }
}
