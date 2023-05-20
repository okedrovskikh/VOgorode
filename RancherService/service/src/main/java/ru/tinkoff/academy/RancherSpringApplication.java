package ru.tinkoff.academy;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class RancherSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(RancherSpringApplication.class, args);
    }
}
