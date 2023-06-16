package ru.tinkoff.academy.configuration;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.tinkoff.academy.proto.order.OrderInformResponse;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;

import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {
    @Bean
    public KafkaTemplate<String, WorkerJobRequest> workerRequestKafkaTemplate(ProducerFactory<String, WorkerJobRequest> workerResponseProducerFactory) {
        return new KafkaTemplate<>(workerResponseProducerFactory);
    }

    @Bean
    public ProducerFactory<String, WorkerJobRequest> workerRequestProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig(), StringSerializer::new, KafkaProtobufSerializer::new);
    }

    @Bean
    public KafkaTemplate<String, OrderInformResponse> orderResponseKafkaTemplate(ProducerFactory<String, OrderInformResponse> orderResponseProducerFactory) {
        return new KafkaTemplate<>(orderResponseProducerFactory);
    }

    @Bean
    public ProducerFactory<String, OrderInformResponse> orderResponseProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig(), StringSerializer::new, KafkaProtobufSerializer::new);
    }

    private Map<String, Object> producerConfig() {
        return Map.of(

        );
    }
}
