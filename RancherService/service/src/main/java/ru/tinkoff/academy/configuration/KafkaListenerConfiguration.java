package ru.tinkoff.academy.configuration;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.tinkoff.academy.properties.kafka.KafkaListenerProperties.OrderInformConsumerProperties;
import ru.tinkoff.academy.proto.order.OrderInformResponse;

@Configuration
public class KafkaListenerConfiguration {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderInformResponse> orderResponseListenerContainerFactory(
            ConsumerFactory<String, OrderInformResponse> orderResponseListenerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderInformResponse>();
        factory.setConsumerFactory(orderResponseListenerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, OrderInformResponse> orderResponseListenerFactory(OrderInformConsumerProperties properties) {
        return new DefaultKafkaConsumerFactory<>(properties.toPropertiesMap(), StringDeserializer::new, KafkaProtobufDeserializer::new);
    }
}
