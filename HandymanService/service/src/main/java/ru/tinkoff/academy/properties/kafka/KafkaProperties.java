package ru.tinkoff.academy.properties.kafka;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Data
@Validated
public abstract sealed class KafkaProperties permits KafkaProducerProperties, KafkaListenerProperties {
    @NotBlank
    protected String kafkaBrokers;
    protected String schemaRegistry;
    @NotBlank
    protected String topic;
    @NotBlank
    private String groupIdPostfix = "1";

    public String getClientId() {
        return String.format("user.%s", topic);
    }

    public String getGroupId() {
        return String.format("%s.%s", getClientId(), groupIdPostfix);
    }

    public abstract Map<String, Object> toPropertiesMap();
}
