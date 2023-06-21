package ru.tinkoff.academy.configuration;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.tinkoff.academy.properties.kafka.KafkaProducerProperties.JobResponseProducerProperties;
import ru.tinkoff.academy.proto.worker.WorkerJobResponse;

@Configuration
public class KafkaProducerConfiguration {

    @Bean
    public KafkaTemplate<String, WorkerJobResponse> workerJobResponseKafkaTemplate(
            ProducerFactory<String, WorkerJobResponse> workerJobResponseProducerFactory
    ) {
        return new KafkaTemplate<>(workerJobResponseProducerFactory);
    }

    @Bean
    public ProducerFactory<String, WorkerJobResponse> workerJobResponseProducerFactory(JobResponseProducerProperties producerProperties) {
        return new DefaultKafkaProducerFactory<>(producerProperties.toPropertiesMap(), StringSerializer::new, KafkaProtobufSerializer::new);
    }
}
