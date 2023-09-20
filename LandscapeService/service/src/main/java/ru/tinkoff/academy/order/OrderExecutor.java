package ru.tinkoff.academy.order;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.handyman.worker.Worker;
import ru.tinkoff.academy.handyman.worker.WorkerService;
import ru.tinkoff.academy.order.status.OrderStatus;
import ru.tinkoff.academy.rancher.order.OrderInformClient;
import ru.tinkoff.academy.site.Site;
import ru.tinkoff.academy.site.SiteService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class OrderExecutor {
    private final OrderService orderService;
    private final WorkerService workerService;
    private final SiteService siteService;
    private final OrderInformClient orderInformClient;

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    @Transactional
    public void searchForWorker() {
        List<Order> orders = orderService.findAllByStatus(OrderStatus.created);

        for (Order order : orders) {
            Site site = siteService.getById(order.getGardenId());
            Optional<Worker> worker = workerService.findWorker(Arrays.asList(order.getWorks()), site.getLatitude(), site.getLongitude());

            if (worker.isPresent()) {
                orderService.updateWorkerId(order.getId(), worker.get().getLandscapeId());
            } else {
                orderInformClient.inform(order.getId(), order.getGardenId().toString(), ru.tinkoff.academy.proto.order.OrderStatus.reject);
            }
        }
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void searchForCompletedOrders() {
        List<Order> orders = orderService.findAllByStatus(OrderStatus.done);

        for (Order order : orders) {
            orderInformClient.inform(order.getId(), order.getGardenId().toString(), ru.tinkoff.academy.proto.order.OrderStatus.done);
        }
    }
}
