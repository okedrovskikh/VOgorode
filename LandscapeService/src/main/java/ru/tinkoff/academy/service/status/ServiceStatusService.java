package ru.tinkoff.academy.service.status;

import com.google.protobuf.Empty;
import io.grpc.ConnectivityState;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.proto.service.status.ServiceStatusGrpc;
import ru.tinkoff.academy.proto.service.status.VersionResponse;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ServiceStatusService {
    @GrpcClient("HandymanService")
    private ServiceStatusGrpc.ServiceStatusBlockingStub handymanBlockingStub;
    @GrpcClient("RancherService")
    private ServiceStatusGrpc.ServiceStatusBlockingStub rancherBlockingStub;

    private final GrpcChannelFactory grpcChannelFactory;

    /**
     * Get connected to defined in application.yml grpc servers statuses
     *
     * @return {@link Map} with service name as key and {@link ServiceStatus} as value
     */
    public Map<String, List<ServiceStatus>> getServicesStatuses() {
        return Map.of(
                "HandymanService", List.of(getServiceStatus(handymanBlockingStub, "HandymanService")),
                "RancherService", List.of(getServiceStatus(rancherBlockingStub, "RancherService"))
        );
    }

    private ServiceStatus getServiceStatus(ServiceStatusGrpc.ServiceStatusBlockingStub serviceStatusBlockingStub,
                                           String serviceName) {
        ConnectivityState connectivityState = grpcChannelFactory.getConnectivityState().get(serviceName);

        if (isConnectionOk(connectivityState)) {
            try {
                VersionResponse versionResponse = serviceStatusBlockingStub.withDeadlineAfter(100, TimeUnit.SECONDS)
                        .getVersion(Empty.getDefaultInstance());
                return ServiceStatus.builder()
                        .host(serviceStatusBlockingStub.getChannel().authority())
                        .status(connectivityState.name())
                        .artifact(versionResponse.getArtifact())
                        .name(versionResponse.getName())
                        .group(versionResponse.getGroup())
                        .version(versionResponse.getVersion())
                        .build();
            } catch (StatusRuntimeException e) {
                return buildNotConnectedServiceStatus(serviceStatusBlockingStub.getChannel().authority(),
                        grpcChannelFactory.getConnectivityState().get(serviceName).name());
            }
        }
        return buildNotConnectedServiceStatus(serviceStatusBlockingStub.getChannel().authority(),
                grpcChannelFactory.getConnectivityState().get(serviceName).name());
    }
    
    private boolean isConnectionOk(ConnectivityState connectivityState) {
        return !connectivityState.equals(ConnectivityState.SHUTDOWN) && !connectivityState.equals(ConnectivityState.TRANSIENT_FAILURE);
    }

    private ServiceStatus buildNotConnectedServiceStatus(String host, String connectionState) {
        return ServiceStatus.builder()
                .host(host)
                .status(connectionState)
                .build();
    }
}
