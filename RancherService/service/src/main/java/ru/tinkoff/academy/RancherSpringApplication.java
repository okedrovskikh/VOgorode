package ru.tinkoff.academy;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.academy.properties.kafka.KafkaListenerProperties;

@EnableMongock
@SpringBootApplication
@EnableConfigurationProperties({KafkaListenerProperties.OrderInformConsumerProperties.class})
public class RancherSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(RancherSpringApplication.class, args);
    }
}
