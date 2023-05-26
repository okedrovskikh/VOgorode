package ru.tinkoff.academy.order;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.handyman.worker.Worker;
import ru.tinkoff.academy.handyman.worker.WorkerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class OrderExecutor {
    private final OrderService orderService;
    private final WorkerService workerService;

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void searchForWorker() {
        List<Order> orders = orderService.findAllCreated();

        for (Order order : orders) {
            Optional<Worker> worker = workerService.findWorker(Arrays.asList(order.getWorks()), null, null);

            if (worker.isPresent()) {
                orderService.updateWorkerId(order.getId(), worker.get().getId());
            } else {

            }
        }
    }
}
