package ru.tinkoff.academy.order;

import ru.tinkoff.academy.proto.order.OrderInformResponse;

public class OrderUtils {
    public static OrderInformResponse buildOrderInform(Long orderId, OrderInformResponse.OrderStatus orderStatus) {
        return OrderInformResponse.newBuilder()
                .setOrderId(orderId)
                .setStatus(orderStatus)
                .build();
    }
}
