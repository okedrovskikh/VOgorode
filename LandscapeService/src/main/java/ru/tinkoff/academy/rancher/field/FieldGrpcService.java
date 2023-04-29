package ru.tinkoff.academy.rancher.field;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.field.AreaStatRequest;
import ru.tinkoff.academy.proto.field.AreaStatResponse;
import ru.tinkoff.academy.proto.field.FieldServiceGrpc;


@Component
public class FieldGrpcService {
    @GrpcClient("RancherService")
    private FieldServiceGrpc.FieldServiceBlockingStub fieldServiceBlockingStub;

    public AreaStatResponse getAreasStatSplitByEmailAndTelephone() {
        return fieldServiceBlockingStub.getAreasStatSplitByEmailAndTelephone(Empty.getDefaultInstance());
    }

    public AreaStatResponse getAreasStatBySplitValue(AreaStatRequest request) {
        return fieldServiceBlockingStub.getAreasStatBySplitValue(request);
    }
}
