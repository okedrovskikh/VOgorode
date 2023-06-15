package ru.tinkoff.academy.rancher.order;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.order.OrderInformRequest;
import ru.tinkoff.academy.proto.order.OrderInformServiceGrpc;
import ru.tinkoff.academy.proto.order.OrderStatus;

@Component
@RequiredArgsConstructor
public class OrderInformClient {
    @GrpcClient("RancherService")
    private OrderInformServiceGrpc.OrderInformServiceBlockingStub orderInformServiceBlockingStub;

    public void inform(Long orderId, String gardenId, OrderStatus status) {
        orderInformServiceBlockingStub.informStatus(OrderInformRequest.newBuilder()
                .setId(gardenId)
                .setStatus(status)
                .setOrderId(orderId)
                .build()
        );
    }
}
