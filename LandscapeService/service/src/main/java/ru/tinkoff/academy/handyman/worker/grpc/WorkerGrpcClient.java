package ru.tinkoff.academy.handyman.worker.grpc;

import com.google.protobuf.Empty;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.work.WorkEnum;
import ru.tinkoff.academy.proto.worker.WorkerByServicesRequest;
import ru.tinkoff.academy.proto.worker.WorkerResponse;
import ru.tinkoff.academy.proto.worker.WorkerServiceGrpc;

import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class WorkerGrpcClient {
    @GrpcClient("HandymanService")
    private WorkerServiceGrpc.WorkerServiceBlockingStub workerServiceBlockingStub;

    public Iterator<WorkerResponse> findAll() {
        return workerServiceBlockingStub.findAll(Empty.getDefaultInstance());
    }

    public Iterator<WorkerResponse> findByServices(Collection<WorkEnum> services) {
        return workerServiceBlockingStub.findByServices(WorkerByServicesRequest.newBuilder()
                .addAllServices(services).build());
    }
}
