package ru.tinkoff.academy.service.status;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.info.BuildProperties;
import ru.tinkoff.academy.proto.service.status.ReadinessResponse;
import ru.tinkoff.academy.proto.service.status.ServiceStatusGrpc;
import ru.tinkoff.academy.proto.service.status.VersionResponse;
import ru.tinkoff.academy.system.status.SystemStatusService;

@GrpcService
@RequiredArgsConstructor
public class ServiceStatusImpl extends ServiceStatusGrpc.ServiceStatusImplBase {
    private final BuildProperties buildProperties;
    private final SystemStatusService systemStatusService;

    /**
     * Get Readiness state of server
     *
     * @param request          empty gRPC request to server
     * @param responseObserver response observer for sending stream message
     */
    @Override
    public void getReadiness(Empty request, StreamObserver<ReadinessResponse> responseObserver) {
        ReadinessResponse readinessResponse = ReadinessResponse.newBuilder()
                .setStatus(systemStatusService.getSystemStatus().name())
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
                .setVersion(this.buildProperties.getVersion())
                .setGroup(this.buildProperties.getGroup())
                .setName(this.buildProperties.getName())
                .setArtifact(this.buildProperties.getArtifact())
                .build();
        responseObserver.onNext(versionResponse);
        responseObserver.onCompleted();
    }
}
