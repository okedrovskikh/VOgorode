package ru.tinkoff.academy.handyman.worker.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.order.OrderService;
import ru.tinkoff.academy.order.status.OrderStatus;
import ru.tinkoff.academy.proto.order.OrderInformResponse;
import ru.tinkoff.academy.proto.worker.WorkerJobResponse;
import ru.tinkoff.academy.order.OrderInformKafkaProducer;

import static ru.tinkoff.academy.order.OrderUtils.buildOrderInform;

@Component
@RequiredArgsConstructor
public class WorkerJobResponseListener {
    private final OrderService orderService;
    private final OrderInformKafkaProducer orderInformKafkaProducer;

    @KafkaListener(
            id = "jobResponseListener",
            idIsGroup = false,
            topics = "vogorode.job-response.queue",
            containerFactory = "workerJobResponseListenerContainerFactory"
    )
    public void listen(@Payload WorkerJobResponse event) {
        if (event.getStatus().equals(WorkerJobResponse.WorkerJobEnum.done)) {
            orderService.updateStatus(event.getId(), OrderStatus.done);
            orderInformKafkaProducer.sendEvent(buildOrderInform(event.getId(), OrderInformResponse.OrderStatus.done));
        } else {
            orderInformKafkaProducer.sendEvent(buildOrderInform(event.getId(), OrderInformResponse.OrderStatus.rejected));
        }
    }
}
