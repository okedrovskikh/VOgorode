package ru.tinkoff.academy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.tinkoff.academy.proto.worker.WorkerJobResponse;

import java.util.Map;

@Configuration
public class KafkaListenerConfiguration {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WorkerJobResponse> workerResponseListenerContainerFactory(
            ConsumerFactory<String, WorkerJobResponse> workerResponseListenerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, WorkerJobResponse>();
        factory.setConsumerFactory(workerResponseListenerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, WorkerJobResponse> workerResponseListenerFactory() {
        return new DefaultKafkaConsumerFactory<>(listenerProperties());
    }

    private Map<String, Object> listenerProperties() {
        return Map.of();
    }
}