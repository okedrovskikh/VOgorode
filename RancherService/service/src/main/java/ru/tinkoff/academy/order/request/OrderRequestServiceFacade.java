package ru.tinkoff.academy.order.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.exceptions.OrderCompletedException;
import ru.tinkoff.academy.exceptions.OrderIncompleteException;
import ru.tinkoff.academy.garden.Garden;
import ru.tinkoff.academy.garden.GardenService;
import ru.tinkoff.academy.landscape.order.Order;
import ru.tinkoff.academy.landscape.order.OrderWebClientHelper;
import ru.tinkoff.academy.landscape.order.dto.OrderCreateDto;
import ru.tinkoff.academy.landscape.order.dto.OrderUpdateDto;
import ru.tinkoff.academy.landscape.order.dto.StatusUpdateDto;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderRequestServiceFacade {
    private final GardenService gardenService;
    private final OrderWebClientHelper orderWebClientHelper;
    private final OrderRequestService orderRequestService;

    public Order create(String gardenId) {
        Garden garden = gardenService.getById(gardenId);
        Optional<OrderStatus> status = orderRequestService.findByGardenId(gardenId);

        Order order;
        if (status.isPresent()) {
            OrderStatus orderStatus = status.get();

            if (orderStatus.getStatus().equals(OrderRequestStatus.done)) {
                throw new OrderCompletedException(String.format("Order for garden with id: %s completed", garden.getId()));
            }

            orderStatus.setStatus(OrderRequestStatus.created);
            order = orderWebClientHelper.updateOrder(OrderUpdateDto.builder()
                    .id(orderStatus.getOrderId())
                    .works(garden.getWorks().toArray(WorkEnum[]::new))
                    .build()
            ).block();
            orderRequestService.update(orderStatus);
        } else {
            order = orderWebClientHelper.createOrder(OrderCreateDto.builder()
                    .gardenId(garden.getSiteId())
                    .works(garden.getWorks())
                    .build()
            ).block();
            orderRequestService.save(order, garden);
        }

        return order;
    }

    public OrderStatus getStatus(String gardenId) {
        return orderRequestService.getByGardenId(gardenId);
    }

    public void approve(String gardenId) {
        OrderStatus orderStatus = orderRequestService.getByGardenId(gardenId);

        if (!orderStatus.getStatus().equals(OrderRequestStatus.done)) {
            throw new OrderIncompleteException(String.format("Order for garden with id: %s incompleted", gardenId));
        }

        orderWebClientHelper.updateOrderStatus(StatusUpdateDto.builder()
                .id(orderStatus.getOrderId())
                .status(ru.tinkoff.academy.landscape.order.status.OrderStatus.done)
                .build()
        ).block();

        orderRequestService.delete(orderStatus);
    }
}
