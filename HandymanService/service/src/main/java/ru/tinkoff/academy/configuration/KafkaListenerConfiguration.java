package ru.tinkoff.academy.configuration;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.tinkoff.academy.properties.kafka.KafkaListenerProperties.JobRequestConsumerProperties;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;

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
    public ConsumerFactory<String, WorkerJobRequest> workerJobRequestListenerFactory(JobRequestConsumerProperties properties) {
        return new DefaultKafkaConsumerFactory<>(properties.toPropertiesMap(), StringDeserializer::new, KafkaProtobufDeserializer::new);
    }
}
