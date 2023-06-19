package ru.tinkoff.academy.handyman.worker.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.proto.order.OrderInformResponse;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;

import static ru.tinkoff.academy.rancher.order.OrderUtils.buildOrderInform;

@Service
@RequiredArgsConstructor
public class WorkerRequestKafkaProducer {
    private final KafkaTemplate<String, WorkerJobRequest> workerRequestKafkaTemplate;
    private final KafkaTemplate<String, OrderInformResponse> orderResponseKafkaTemplate;

    public void sendEvent(WorkerJobRequest event) {
        workerRequestKafkaTemplate.send("topic", event).whenComplete((res, ex) -> {
            if (ex != null) {
                orderResponseKafkaTemplate.send(
                        "topic", buildOrderInform(event.getOrderId(), OrderInformResponse.OrderStatus.rejected)
                );
            }
        });
    }
}
