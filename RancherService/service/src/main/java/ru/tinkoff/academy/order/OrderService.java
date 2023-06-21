package ru.tinkoff.academy.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.exceptions.OrderCompletedException;
import ru.tinkoff.academy.garden.Garden;
import ru.tinkoff.academy.landscape.order.Order;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderStatus save(Order order, Garden garden) {
        Optional<OrderStatus> optionalOrderStatus = orderRepository.findByGardenId(garden.getId());

        OrderStatus orderStatus;
        if (optionalOrderStatus.isPresent()) {
            orderStatus = optionalOrderStatus.get();

            if (orderStatus.getStatus().equals(OrderCreationStatus.done)) {
                throw new OrderCompletedException(String.format("Order for garden with id: %s completed", garden.getId()));
            }

        } else {
            orderStatus = OrderStatus.builder()
                    .orderId(order.getId())
                    .gardenId(garden.getId())
                    .status(OrderCreationStatus.created)
                    .build();
        }

        return orderRepository.save(orderStatus);
    }

    public OrderStatus getByGardenId(String gardenId) {
        return findByGardenId(gardenId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Order for garden with id: %s not found", gardenId)));
    }

    public Optional<OrderStatus> findByGardenId(String gardenId) {
        return orderRepository.findByGardenId(gardenId);
    }

    public void delete(OrderStatus orderStatus) {
        orderRepository.delete(orderStatus);
    }
}
