package ru.tinkoff.academy.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.exceptions.OrderIncompleteException;
import ru.tinkoff.academy.garden.Garden;
import ru.tinkoff.academy.garden.GardenService;
import ru.tinkoff.academy.landscape.order.Order;
import ru.tinkoff.academy.landscape.order.OrderWebClientHelper;
import ru.tinkoff.academy.landscape.order.dto.OrderCreateDto;
import ru.tinkoff.academy.landscape.order.dto.StatusUpdateDto;

@Service
@RequiredArgsConstructor
public class OrderServiceFacade {
    private final GardenService gardenService;
    private final OrderWebClientHelper orderWebClientHelper;
    private final OrderService orderService;

    public Order create(String gardenId) {
        Garden garden = gardenService.getById(gardenId);
        Order order = orderWebClientHelper.createOrder(OrderCreateDto.builder()
                .gardenId(garden.getSiteId())
                .works(garden.getWorks())
                .build()
        ).block();
        orderService.save(order, garden);
        return order;
    }

    public OrderStatus getStatus(String gardenId) {
        return orderService.getByGardenId(gardenId);
    }

    public void approve(String gardenId) {
        OrderStatus orderStatus = orderService.getByGardenId(gardenId);

        if (!orderStatus.getStatus().equals(OrderCreationStatus.done)) {
            throw new OrderIncompleteException(String.format("Order for garden with id: %s incompleted", gardenId));
        }

        orderWebClientHelper.updateOrderStatus(StatusUpdateDto.builder()
                .id(orderStatus.getOrderId())
                .status(ru.tinkoff.academy.landscape.order.status.OrderStatus.done)
                .build()
        ).block();

        orderService.delete(orderStatus);
    }
}
