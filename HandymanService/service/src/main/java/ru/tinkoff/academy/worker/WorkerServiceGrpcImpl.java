package ru.tinkoff.academy.worker;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.landscape.order.OrderWebClientHelper;
import ru.tinkoff.academy.landscape.order.dto.StatusUpdateDto;
import ru.tinkoff.academy.landscape.order.status.OrderStatus;
import ru.tinkoff.academy.proto.worker.WorkerByServicesRequest;
import ru.tinkoff.academy.proto.worker.WorkerJobEnum;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;
import ru.tinkoff.academy.proto.worker.WorkerJobResponse;
import ru.tinkoff.academy.proto.worker.WorkerResponse;
import ru.tinkoff.academy.proto.worker.WorkerServiceGrpc;
import ru.tinkoff.academy.work.WorkEnum;
import ru.tinkoff.academy.work.WorkEnumMapper;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class WorkerServiceGrpcImpl extends WorkerServiceGrpc.WorkerServiceImplBase {
    private final WorkerService workerService;
    private final WorkerMapper workerMapper;
    private final WorkEnumMapper workEnumMapper;
    private final OrderWebClientHelper orderWebClientHelper;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void findAll(Empty request, StreamObserver<WorkerResponse> responseObserver) {
        workerService.findAll().stream()
                .map(workerMapper::mapToGrpcResponse)
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void findByServices(WorkerByServicesRequest request, StreamObserver<WorkerResponse> responseObserver) {
        Set<WorkEnum> services = request.getServicesList().stream()
                .map(workEnumMapper::fromGrpcWorkEnum)
                .collect(Collectors.toSet());
        workerService.findAllByServices(services)
                .stream()
                .map(workerMapper::mapToGrpcResponse)
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void createRequest(WorkerJobRequest request, StreamObserver<WorkerJobResponse> responseObserver) {
        // hardcoded for testing
        Random random = new Random();

        if (Double.compare(random.nextDouble(), 0.2) >= 0) {
            responseObserver.onNext(WorkerJobResponse.newBuilder().setDecision(WorkerJobEnum.accepted).build());
            executor.execute(() -> {
                try {
                    Thread.sleep(random.nextInt() % 6000);
                    orderWebClientHelper.updateOrderStatus(StatusUpdateDto.builder()
                            .id(request.getId())
                            .status(OrderStatus.done)
                            .build()
                    ).block();
                } catch (InterruptedException ignored) {
                }
            });
        } else {
            responseObserver.onNext(WorkerJobResponse.newBuilder().setDecision(WorkerJobEnum.rejected).build());
        }

        responseObserver.onCompleted();
    }
}
