package ru.tinkoff.academy.order.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.exceptions.OrderCompletedException;
import ru.tinkoff.academy.garden.Garden;
import ru.tinkoff.academy.landscape.order.Order;
import ru.tinkoff.academy.proto.order.OrderInformResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderRequestService {
    private final OrderRequestRepository orderRequestRepository;

    public OrderStatus save(Order order, Garden garden) {
        Optional<OrderStatus> optionalOrderStatus = orderRequestRepository.findByGardenId(garden.getId());

        OrderStatus orderStatus;
        if (optionalOrderStatus.isPresent()) {
            orderStatus = optionalOrderStatus.get();

            if (orderStatus.getStatus().equals(OrderRequestStatus.done)) {
                throw new OrderCompletedException(String.format("Order for garden with id: %s completed", garden.getId()));
            }

        } else {
            orderStatus = OrderStatus.builder()
                    .orderId(order.getId())
                    .gardenId(garden.getId())
                    .status(OrderRequestStatus.created)
                    .build();
        }

        return orderRequestRepository.save(orderStatus);
    }

    public OrderStatus getByGardenId(String gardenId) {
        return findByGardenId(gardenId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Order for garden with id: %s not found", gardenId)));
    }

    public Optional<OrderStatus> findByGardenId(String gardenId) {
        return orderRequestRepository.findByGardenId(gardenId);
    }

    public OrderStatus getByOrderId(Long orderId) {
        return findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Order status for order with id: %s not found", orderId)));
    }

    public Optional<OrderStatus> findByOrderId(Long orderId) {
        return orderRequestRepository.findByOrderId(orderId);
    }

    public OrderStatus updateStatus(OrderInformResponse event) {
        OrderStatus orderStatus = getByOrderId(event.getOrderId());
        orderStatus.setStatus(OrderRequestStatus.valueOf(event.getStatus().name()));
        return orderRequestRepository.save(orderStatus);
    }

    public void delete(OrderStatus orderStatus) {
        orderRequestRepository.delete(orderStatus);
    }
}
