package ru.tinkoff.academy.rancher;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.rancher.fielder.FielderRequest;
import ru.tinkoff.academy.proto.rancher.fielder.FielderResponse;
import ru.tinkoff.academy.proto.rancher.fielder.FielderServiceGrpc;

@Component
public class FielderGrpcService {
    @GrpcClient("RancherService")
    private FielderServiceGrpc.FielderServiceBlockingStub fielderServiceBlockingStub;

    public FielderResponse getByEmailOrTelephone(FielderRequest request) {
        return fielderServiceBlockingStub.getByEmailOrTelephone(request);
    }
}
