package ru.tinkoff.academy.gardener.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.landscape.order.Order;
import ru.tinkoff.academy.landscape.order.OrderWebClientHelper;
import ru.tinkoff.academy.landscape.order.dto.OrderCreateDto;

@Service
@RequiredArgsConstructor
public class OrderCreationService {
    private final OrderWebClientHelper orderWebClientHelper;

    public Order create(OrderCreateDto createDto) {
        return orderWebClientHelper.createOrder(createDto).block();
    }
}
