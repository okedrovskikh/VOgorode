package ru.tinkoff.academy.handyman.worker.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.order.OrderInformKafkaProducer;
import ru.tinkoff.academy.properties.kafka.KafkaProducerProperties;
import ru.tinkoff.academy.proto.order.OrderInformResponse;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;

import static ru.tinkoff.academy.order.OrderUtils.buildOrderInform;

@Service
@RequiredArgsConstructor
public class WorkerRequestKafkaProducer {
    private final KafkaTemplate<String, WorkerJobRequest> workerRequestKafkaTemplate;
    private final OrderInformKafkaProducer orderInformKafkaProducer;
    private final KafkaProducerProperties.JobRequestProducerProperties properties;

    public void sendEvent(WorkerJobRequest event) {
        workerRequestKafkaTemplate.send(properties.getTopic(), event).whenComplete((res, ex) -> {
            if (ex != null) {
                orderInformKafkaProducer.sendEvent(buildOrderInform(event.getOrderId(), OrderInformResponse.OrderStatus.rejected));
            }
        });
    }
}
