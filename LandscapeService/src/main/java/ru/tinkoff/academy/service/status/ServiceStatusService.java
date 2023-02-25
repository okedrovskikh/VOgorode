package ru.tinkoff.academy.service.status;

import com.google.protobuf.Empty;
import io.grpc.Channel;
import io.grpc.ConnectivityState;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.proto.ReadinessResponse;
import ru.tinkoff.academy.proto.ServiceStatusGrpc;
import ru.tinkoff.academy.proto.VersionResponse;

import java.util.ArrayList;
import java.util.HashMap;
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
<<<<<<< HEAD
        Map<String, List<ServiceStatus>> connectedToServicesStatus = new HashMap<>();
        for (String connectedToServiceName : this.grpcChannelsProperties.getClient().keySet()) {
            String serviceName = mapValidServiceName(connectedToServiceName);

            if (isServiceNameInvalid(connectedToServiceName)) {
                continue;
            }

            connectedToServicesStatus.computeIfAbsent(connectedToServiceName, key -> new ArrayList<>());
            connectedToServicesStatus.get(connectedToServiceName).add(getServiceStatus(connectedToServiceName));
        }
        return connectedToServicesStatus;
    }

    private boolean isServiceNameInvalid(String serviceName) {
        final String excludedGlobalName = "GLOBAL";
        return serviceName.equals(excludedGlobalName);
    }

    private ServiceStatus getServiceStatus(String serviceName) {
        Channel serviceChannel = this.grpcChannelFactory.createChannel(serviceName);
        ConnectivityState connectivityState = this.grpcChannelFactory.getConnectivityState().get(serviceName);

        if (isConnectionOk(connectivityState)) {
            return getServiceStatus(serviceChannel);
        }

        return ServiceStatus.builder()
                .host(serviceChannel.authority())
                .status(connectivityState.name())
                .build();
    }

    private boolean isConnectionOk(ConnectivityState connectivityState) {
        return !connectivityState.equals(ConnectivityState.SHUTDOWN) && !connectivityState.equals(ConnectivityState.TRANSIENT_FAILURE);
    }

    private ServiceStatus getServiceStatus(Channel serviceChannel) {
        ServiceStatusGrpc.ServiceStatusBlockingStub serviceStatusBlockingStub = ServiceStatusGrpc.newBlockingStub(serviceChannel);
=======
        return Map.of(
                "HandymanService", List.of(getServiceStatus(handymanBlockingStub, "HandymanService")),
                "RancherService", List.of(getServiceStatus(rancherBlockingStub, "RancherService"))
        );
    }

    private ServiceStatus getServiceStatus(ServiceStatusGrpc.ServiceStatusBlockingStub serviceStatusBlockingStub,
                                           String serviceName) {
>>>>>>> 51c8e6f (fixes)
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
