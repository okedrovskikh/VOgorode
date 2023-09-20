package ru.tinkoff.academy.gardener;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.event.Event;
import ru.tinkoff.academy.event.EventService;
import ru.tinkoff.academy.event.EventStatus;
import ru.tinkoff.academy.landscape.order.OrderWebClientHelper;
import ru.tinkoff.academy.landscape.order.dto.StatusUpdateDto;
import ru.tinkoff.academy.landscape.order.status.OrderStatus;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class GardenerExecutor {
    private final EventService eventService;
    private final OrderWebClientHelper orderWebClientHelper;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void updateOrderStatus() {
        // hardcoded for testing
        Random random = new Random();
        List<Event> events = eventService.findAllByStatus(EventStatus.done);

        events.forEach(e -> executor.execute(() -> {
                    try {
                        Thread.sleep(random.nextInt() % 60000);
                        orderWebClientHelper.updateOrderStatus(StatusUpdateDto.builder()
                                .id(e.getIdentityKey())
                                .status(OrderStatus.approved)
                                .build()).block();
                        eventService.update(e.getId(), EventStatus.approved);
                    } catch (Exception ignored) {
                    }
                })
        );
    }
}
