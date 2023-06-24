package ru.tinkoff.academy.configuration;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.tinkoff.academy.properties.kafka.KafkaListenerProperties.JobResponseConsumerProperties;
import ru.tinkoff.academy.proto.worker.WorkerJobResponse;

@Configuration
public class KafkaListenerConfiguration {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WorkerJobResponse> workerJobResponseListenerContainerFactory(
            ConsumerFactory<String, WorkerJobResponse> workerJobResponseListenerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, WorkerJobResponse>();
        factory.setConsumerFactory(workerJobResponseListenerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, WorkerJobResponse> workerJobResponseListenerFactory(JobResponseConsumerProperties properties) {
        return new DefaultKafkaConsumerFactory<>(properties.toPropertiesMap(), StringDeserializer::new, KafkaProtobufDeserializer::new);
    }
}
