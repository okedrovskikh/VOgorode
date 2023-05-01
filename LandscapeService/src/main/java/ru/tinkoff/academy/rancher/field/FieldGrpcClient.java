package ru.tinkoff.academy.rancher.field;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.field.AreaStatRequest;
import ru.tinkoff.academy.proto.field.AreaStatResponse;
import ru.tinkoff.academy.proto.field.FieldServiceGrpc;

import java.util.Iterator;

@Component
public class FieldGrpcClient {
    @GrpcClient("RancherService")
    private FieldServiceGrpc.FieldServiceBlockingStub fieldServiceBlockingStub;

    public Iterator<AreaStatResponse> getAreasStatSplitByEmailAndTelephoneStream() {
        return fieldServiceBlockingStub.getAreasStatSplitByEmailAndTelephone(Empty.getDefaultInstance());
    }

    public Iterator<AreaStatResponse> getAreasStatBySplitValueStream(AreaStatRequest request) {
        return fieldServiceBlockingStub.getAreasStatBySplitValue(request);
    }
}
