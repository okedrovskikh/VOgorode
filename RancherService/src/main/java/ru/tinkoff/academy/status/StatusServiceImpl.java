package ru.tinkoff.academy.status;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.info.BuildProperties;
import ru.tinkoff.academy.proto.ReadinessResponse;
import ru.tinkoff.academy.proto.StatusServiceGrpc;
import ru.tinkoff.academy.proto.VersionResponse;

@GrpcService
@RequiredArgsConstructor
public class StatusServiceImpl extends StatusServiceGrpc.StatusServiceImplBase {
    private final BuildProperties buildProperties;

    @Override
    public void getReadiness(Empty request, StreamObserver<ReadinessResponse> responseObserver) {
        ReadinessResponse readinessResponse = ReadinessResponse.newBuilder()
                .setStatus("OK")
                .build();
        responseObserver.onNext(readinessResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getVersion(Empty request, StreamObserver<VersionResponse> responseObserver) {
        VersionResponse versionResponse = VersionResponse.newBuilder()
                .setVersion(buildProperties.getVersion())
                .setGroup(buildProperties.getGroup())
                .setName(buildProperties.getName())
                .setArtifact(buildProperties.getArtifact())
                .build();
        responseObserver.onNext(versionResponse);
        responseObserver.onCompleted();
    }
}
