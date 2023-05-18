package ru.tinkoff.academy.rancher.area;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.area.AreaServiceGrpc;
import ru.tinkoff.academy.proto.area.AreaStatRequest;
import ru.tinkoff.academy.proto.area.AreaStatResponse;

import java.util.Iterator;

@Component
public class AreaGrpcClient {
    @GrpcClient("RancherService")
    private AreaServiceGrpc.AreaServiceBlockingStub areaServiceBlockingStub;

    public Iterator<AreaStatResponse> getAreasStatSplitByEmailAndTelephoneStream() {
        return areaServiceBlockingStub.getAreasStatSplitByEmailAndTelephone(Empty.getDefaultInstance());
    }

    public Iterator<AreaStatResponse> getAreasStatBySplitValueStream(AreaStatRequest request) {
        return areaServiceBlockingStub.getAreasStatBySplitValue(request);
    }
}
