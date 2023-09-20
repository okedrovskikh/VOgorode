package ru.tinkoff.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.academy.properties.kafka.KafkaListenerProperties.JobResponseConsumerProperties;
import ru.tinkoff.academy.properties.kafka.KafkaProducerProperties.JobRequestProducerProperties;
import ru.tinkoff.academy.properties.kafka.KafkaProducerProperties.OrderInformProducerProperties;

@SpringBootApplication
@EnableConfigurationProperties({JobResponseConsumerProperties.class, JobRequestProducerProperties.class, OrderInformProducerProperties.class})
public class LandscapeSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(LandscapeSpringApplication.class, args);
    }
}
