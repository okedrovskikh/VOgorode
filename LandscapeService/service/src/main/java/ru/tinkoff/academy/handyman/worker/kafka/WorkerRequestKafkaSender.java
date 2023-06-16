package ru.tinkoff.academy.handyman.worker.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.handyman.worker.grpc.Worker;
import ru.tinkoff.academy.handyman.worker.grpc.WorkerService;
import ru.tinkoff.academy.order.Order;
import ru.tinkoff.academy.proto.order.OrderInformResponse;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;
import ru.tinkoff.academy.site.Site;
import ru.tinkoff.academy.site.SiteService;
import ru.tinkoff.academy.work.WorkEnumMapper;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerRequestKafkaSender {
    private final SiteService siteService;
    private final WorkerService workerService;
    private final WorkEnumMapper workEnumMapper;
    private final KafkaTemplate<String, WorkerJobRequest> workerRequestKafkaTemplate;
    private final KafkaTemplate<String, OrderInformResponse> orderResponseKafkaTemplate;

    public void sendEvent(Order order) {
        Site site = siteService.getById(order.getGardenId());
        Optional<Worker> workerOptional = workerService.findWorker(Arrays.asList(order.getWorks()), site.getLatitude(), site.getLongitude());

        if (workerOptional.isPresent()) {
            Worker worker = workerOptional.get();
            WorkerJobRequest event = WorkerJobRequest.newBuilder()
                    .setId(worker.getId())
                    .addAllServices(Arrays.stream(order.getWorks()).map(workEnumMapper::toGrpcEnum).toList())
                    .build();
            workerRequestKafkaTemplate.send("topic", event);
        } else {
            OrderInformResponse event = OrderInformResponse.newBuilder()
                    .setId(order.getGardenId().toString())
                    .setStatus(OrderInformResponse.OrderStatus.rejected)
                    .setOrderId(order.getId())
                    .build();
            orderResponseKafkaTemplate.send("topic", event);
        }
    }
}
