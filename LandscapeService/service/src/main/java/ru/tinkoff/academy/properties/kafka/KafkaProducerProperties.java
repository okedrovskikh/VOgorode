package ru.tinkoff.academy.properties.kafka;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializerConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public sealed class KafkaProducerProperties extends KafkaProperties {

    @Override
    public Map<String, Object> toPropertiesMap() {
        return new HashMap<>() {{
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers);
            put(KafkaProtobufSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        }};
    }

    @ConfigurationProperties(prefix = "kafka.producer.job-request")
    public static final class JobRequestProducerProperties extends KafkaProducerProperties {
    }

    @ConfigurationProperties(prefix = "kafka.producer.order-inform")
    public static final class OrderInformProducerProperties extends KafkaProducerProperties {
    }
}
