package ru.tinkoff.academy.order.request;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.order.OrderInformResponse;

@Component
@RequiredArgsConstructor
public class OrderInformKafkaListener {
    private final OrderRequestService orderRequestService;

    @KafkaListener(
            id = "orderInformListener",
            idIsGroup = false,
            topics = "vogorode.order-inform.queue",
            containerFactory = "orderResponseListenerContainerFactory"
    )
    public void listen(@Payload OrderInformResponse event) {
        orderRequestService.updateStatus(event);
    }
}
