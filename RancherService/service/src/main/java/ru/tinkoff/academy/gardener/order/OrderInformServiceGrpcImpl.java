package ru.tinkoff.academy.gardener.order;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.landscape.order.OrderWebClientHelper;
import ru.tinkoff.academy.landscape.order.dto.StatusUpdateDto;
import ru.tinkoff.academy.landscape.order.status.OrderStatus;
import ru.tinkoff.academy.proto.order.OrderInformRequest;
import ru.tinkoff.academy.proto.order.OrderInformServiceGrpc;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@GrpcService
@RequiredArgsConstructor
public class OrderInformServiceGrpcImpl extends OrderInformServiceGrpc.OrderInformServiceImplBase {
    private final OrderWebClientHelper orderWebClientHelper;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void informStatus(OrderInformRequest request, StreamObserver<Empty> responseObserver) {
        executor.execute(() -> {
            Random random = new Random();
            try {
                Thread.sleep(random.nextInt() % 6000);
            } catch (InterruptedException ignored) {
            }
            orderWebClientHelper.updateOrderStatus(StatusUpdateDto.builder()
                    .id(request.getOrderId())
                    .status(OrderStatus.approved)
                    .build()
            ).block();
        });

        responseObserver.onCompleted();
    }
}
