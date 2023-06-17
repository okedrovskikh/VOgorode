package ru.tinkoff.academy.configuration;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.tinkoff.academy.proto.worker.WorkerJobResponse;

import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {
    @Bean
    public KafkaTemplate<String, WorkerJobResponse> workerJobResponseKafkaTemplate(
            ProducerFactory<String, WorkerJobResponse> workerJobResponseProducerFactory
    ) {
        return new KafkaTemplate<>(workerJobResponseProducerFactory);
    }

    @Bean
    public ProducerFactory<String, WorkerJobResponse> workerJobResponseProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig(), StringSerializer::new, KafkaProtobufSerializer::new);
    }

    private Map<String, Object> producerConfig() {
        return Map.of(

        );
    }
}
