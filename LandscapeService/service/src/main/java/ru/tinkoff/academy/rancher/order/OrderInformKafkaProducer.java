package ru.tinkoff.academy.rancher.order;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.proto.order.OrderInformResponse;

@Service
@RequiredArgsConstructor
public class OrderInformKafkaProducer {
    private final KafkaTemplate<String, OrderInformResponse> orderResponseKafkaTemplate;

    public void sendEvent(OrderInformResponse event) {
        orderResponseKafkaTemplate.send("topic", event);
    }
}
