package ru.tinkoff.academy.worker;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.proto.worker.WorkerResponse;
import ru.tinkoff.academy.proto.worker.WorkerServiceGrpc;

@GrpcService
@RequiredArgsConstructor
public class WorkerServiceGrpcImpl extends WorkerServiceGrpc.WorkerServiceImplBase {
    private final WorkerService workerService;
    private final WorkerMapper workerMapper;

    @Override
    public void findAll(Empty request, StreamObserver<WorkerResponse> responseObserver) {
        workerService.findAll().stream()
                .map(workerMapper::mapToGrpcResponse)
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
