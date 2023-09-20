package ru.tinkoff.academy.order;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.properties.kafka.KafkaProducerProperties;
import ru.tinkoff.academy.proto.order.OrderInformResponse;

@Service
@RequiredArgsConstructor
public class OrderInformKafkaProducer {
    private final KafkaTemplate<String, OrderInformResponse> orderResponseKafkaTemplate;
    private final KafkaProducerProperties.OrderInformProducerProperties properties;

    public void sendEvent(OrderInformResponse event) {
        orderResponseKafkaTemplate.send(properties.getTopic(), event);
    }
}
