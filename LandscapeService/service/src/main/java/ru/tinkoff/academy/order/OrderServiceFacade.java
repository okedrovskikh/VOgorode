package ru.tinkoff.academy.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.handyman.worker.grpc.Worker;
import ru.tinkoff.academy.handyman.worker.grpc.WorkerService;
import ru.tinkoff.academy.handyman.worker.kafka.WorkerRequestKafkaProducer;
import ru.tinkoff.academy.order.dto.OrderCreateDto;
import ru.tinkoff.academy.order.dto.OrderUpdateDto;
import ru.tinkoff.academy.order.dto.StatusUpdateDto;
import ru.tinkoff.academy.proto.order.OrderInformResponse;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;
import ru.tinkoff.academy.rancher.order.OrderInformKafkaProducer;
import ru.tinkoff.academy.site.Site;
import ru.tinkoff.academy.site.SiteService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static ru.tinkoff.academy.rancher.order.OrderUtils.buildOrderInform;

@Service
@RequiredArgsConstructor
public class OrderServiceFacade {
    private final OrderService orderService;
    private final SiteService siteService;
    private final WorkerService workerService;
    private final WorkerRequestKafkaProducer workerRequestKafkaProducer;
    private final OrderInformKafkaProducer orderInformKafkaProducer;

    @Transactional
    public Order save(OrderCreateDto orderCreateDto) {
        Order order = orderService.save(orderCreateDto);
        List<WorkerJobRequest> requests = buildRequests(order);

        if (requests.isEmpty()) {
            orderInformKafkaProducer.sendEvent(buildOrderInform(order.getId(), OrderInformResponse.OrderStatus.rejected));
        } else {
            requests.forEach(workerRequestKafkaProducer::sendEvent);
        }

        return order;
    }

    private List<WorkerJobRequest> buildRequests(Order order) {
        Site site = siteService.getById(order.getGardenId());
        List<Worker> workers = workerService.findWorker(Arrays.asList(order.getWorks()), site.getLatitude(), site.getLongitude());
        return workers.stream().map(worker -> buildRequest(order, worker)).toList();
    }

    private WorkerJobRequest buildRequest(Order order, Worker worker) {
        return WorkerJobRequest.newBuilder()
                .setId(worker.getId())
                .setOrderId(order.getId())
                .build();
    }

    public Order updateWorkerId(Long orderId, UUID workerId) {
        return orderService.updateWorkerId(orderId, workerId);
    }

    public Order getById(Long id) {
        return orderService.getById(id);
    }

    public List<Order> findAll() {
        return orderService.findAll();
    }

    public Page<Order> searchPage(int pageNumber, int pageSize) {
        return orderService.searchPage(pageNumber, pageSize);
    }

    @Transactional
    public Order update(OrderUpdateDto orderUpdateDto) {
        Order order = orderService.update(orderUpdateDto);
        List<WorkerJobRequest> requests = buildRequests(order);

        if (requests.isEmpty()) {
            orderInformKafkaProducer.sendEvent(buildOrderInform(order.getId(), OrderInformResponse.OrderStatus.rejected));
        } else {
            requests.forEach(workerRequestKafkaProducer::sendEvent);
        }

        return order;
    }

    public Order update(StatusUpdateDto statusUpdateDto) {
        return orderService.updateStatus(statusUpdateDto.getId(), statusUpdateDto.getStatus());
    }

    public void delete(Long id) {
        orderService.delete(id);
    }
}
