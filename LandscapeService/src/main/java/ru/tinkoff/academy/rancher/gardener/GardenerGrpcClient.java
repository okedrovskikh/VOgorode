package ru.tinkoff.academy.rancher.gardener;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.gardener.GardenerResponse;
import ru.tinkoff.academy.proto.gardener.GardenerServiceGrpc;

import java.util.Iterator;

@Component
public class GardenerGrpcClient {
    @GrpcClient("RancherService")
    private GardenerServiceGrpc.GardenerServiceBlockingStub gardenerServiceBlockingStub;

    public Iterator<GardenerResponse> findAllGardeners() {
        return gardenerServiceBlockingStub.findAllGardeners(Empty.getDefaultInstance());
    }
}
