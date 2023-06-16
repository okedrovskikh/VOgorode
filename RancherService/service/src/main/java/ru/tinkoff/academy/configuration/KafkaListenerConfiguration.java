package ru.tinkoff.academy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

@Configuration
public class KafkaListenerConfiguration {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> orderResponseListenerContainerFactory(
            ConsumerFactory<String, Object> orderResponseListenerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory(orderResponseListenerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Object> orderResponseListenerFactory() {
        return new DefaultKafkaConsumerFactory<>(listenerProperties());
    }

    private Map<String, Object> listenerProperties() {
        return Map.of();
    }
}
