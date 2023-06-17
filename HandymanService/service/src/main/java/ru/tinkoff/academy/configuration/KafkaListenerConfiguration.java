package ru.tinkoff.academy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;

import java.util.Map;

@Configuration
public class KafkaListenerConfiguration {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WorkerJobRequest> workerJobRequestListenerContainerFactory(
            ConsumerFactory<String, WorkerJobRequest> workerJobRequestListenerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, WorkerJobRequest>();
        factory.setConsumerFactory(workerJobRequestListenerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, WorkerJobRequest> workerJobRequestListenerFactory() {
        return new DefaultKafkaConsumerFactory<>(listenerProperties());
    }

    private Map<String, Object> listenerProperties() {
        return Map.of();
    }
}
