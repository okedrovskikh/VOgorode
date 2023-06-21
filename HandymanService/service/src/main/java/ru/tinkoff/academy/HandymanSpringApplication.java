package ru.tinkoff.academy;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.academy.properties.kafka.KafkaListenerProperties.JobRequestConsumerProperties;
import ru.tinkoff.academy.properties.kafka.KafkaProducerProperties.JobResponseProducerProperties;

@EnableMongock
@SpringBootApplication
@EnableConfigurationProperties({JobRequestConsumerProperties.class, JobResponseProducerProperties.class})
public class HandymanSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(HandymanSpringApplication.class, args);
    }
}
