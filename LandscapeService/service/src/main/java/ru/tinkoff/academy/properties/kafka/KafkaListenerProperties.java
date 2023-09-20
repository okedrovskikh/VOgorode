package ru.tinkoff.academy.properties.kafka;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.tinkoff.academy.proto.worker.WorkerJobResponse;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public sealed class KafkaListenerProperties extends KafkaProperties {

    @Override
    public Map<String, Object> toPropertiesMap() {
        return new HashMap<>() {{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers);
            put(KafkaProtobufDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
            put(ConsumerConfig.CLIENT_ID_CONFIG, getClientId());
            put(ConsumerConfig.GROUP_ID_CONFIG, getGroupId());
            put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        }};
    }

    @ConfigurationProperties(prefix = "kafka.consumer.job-response")
    public static final class JobResponseConsumerProperties extends KafkaListenerProperties {

        @Override
        public Map<String, Object> toPropertiesMap() {
            Map<String, Object> propertiesMap = super.toPropertiesMap();
            propertiesMap.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, WorkerJobResponse.class.getName());
            return propertiesMap;
        }
    }
}
