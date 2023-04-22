package ru.tinkoff.academy.rancher.fielder;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.fielder.FielderRequest;
import ru.tinkoff.academy.proto.fielder.FielderResponse;
import ru.tinkoff.academy.proto.fielder.FielderServiceGrpc;

@Component
public class FielderGrpcService {
    @GrpcClient("RancherService")
    private FielderServiceGrpc.FielderServiceBlockingStub fielderServiceBlockingStub;

    public FielderResponse getByEmailAndTelephone(FielderRequest request) {
        return fielderServiceBlockingStub.getAreasStatByEmailAndTelephone(request);
    }
}
