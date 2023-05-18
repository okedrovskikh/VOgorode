package ru.tinkoff.academy.handyman.worker;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.worker.WorkerResponse;
import ru.tinkoff.academy.proto.worker.WorkerServiceGrpc;

import java.util.Iterator;

@Component
public class WorkerGrpcClient {
    @GrpcClient("HandymanService")
    private WorkerServiceGrpc.WorkerServiceBlockingStub workerServiceBlockingStub;

    public Iterator<WorkerResponse> findAll() {
        return workerServiceBlockingStub.findAll(Empty.getDefaultInstance());
    }
}
