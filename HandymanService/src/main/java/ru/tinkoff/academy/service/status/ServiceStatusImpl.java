package ru.tinkoff.academy.service.status;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.info.BuildProperties;
import ru.tinkoff.academy.proto.ReadinessResponse;
import ru.tinkoff.academy.proto.ServiceStatusGrpc;
import ru.tinkoff.academy.proto.VersionResponse;

@GrpcService
@RequiredArgsConstructor
public class ServiceStatusImpl extends ServiceStatusGrpc.ServiceStatusImplBase {
    private final BuildProperties buildProperties;

    /**
     * Get Readiness state of server
     *
     * @param request          empty gRPC request to server
     * @param responseObserver response observer for sending stream message
     */
    @Override
    public void getReadiness(Empty request, StreamObserver<ReadinessResponse> responseObserver) {
        ReadinessResponse readinessResponse = ReadinessResponse.newBuilder()
                .setStatus("OK")
                .build();
        responseObserver.onNext(readinessResponse);
        responseObserver.onCompleted();
    }

    /**
     * Get build Version of service
     *
     * @param request          empty gRPC request to server
     * @param responseObserver response observer for sending stream message
     */
    @Override
    public void getVersion(Empty request, StreamObserver<VersionResponse> responseObserver) {
        VersionResponse versionResponse = VersionResponse.newBuilder()
                .setArtifact(buildProperties.getArtifact())
                .setName(buildProperties.getName())
                .setGroup(buildProperties.getGroup())
                .setVersion(buildProperties.getVersion())
                .build();
        responseObserver.onNext(versionResponse);
        responseObserver.onCompleted();
    }
}
