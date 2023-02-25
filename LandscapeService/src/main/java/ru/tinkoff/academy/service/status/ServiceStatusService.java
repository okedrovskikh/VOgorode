package ru.tinkoff.academy.service.status;

import com.google.protobuf.Empty;
import io.grpc.Channel;
import io.grpc.ConnectivityState;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.proto.ReadinessResponse;
import ru.tinkoff.academy.proto.ServiceStatusGrpc;
import ru.tinkoff.academy.proto.VersionResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ServiceStatusService {
    private final GrpcChannelsProperties grpcChannelsProperties;
    private final GrpcChannelFactory grpcChannelFactory;

    /**
     * Get connected to defined in application.yml grpc servers statuses
     *
     * @return {@link Map} with service name as key and {@link ServiceStatus} as value
     */
    public Map<String, List<ServiceStatus>> getServicesStatuses() {
        Map<String, List<ServiceStatus>> connectedToServicesStatus = new HashMap<>();
        for (String connectedToServiceName : this.grpcChannelsProperties.getClient().keySet()) {
            String serviceName = mapValidServiceName(connectedToServiceName);

            if (serviceName == null) {
                continue;
            }

            connectedToServicesStatus.computeIfAbsent(serviceName, key -> new ArrayList<>());
            connectedToServicesStatus.get(serviceName).add(getServiceStatus(connectedToServiceName));
        }
        return connectedToServicesStatus;
    }

    private String mapValidServiceName(String serviceName) {
        List<String> mapValidServicesName = List.of("HandymanService", "RancherService");
        for (String mapValidServiceName : mapValidServicesName) {

            if (serviceName.contains(mapValidServiceName)) {
                return mapValidServiceName;
            }

        }
        return null;
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
        ConnectivityState connectivityState = grpcChannelFactory.getConnectivityState().get(serviceName);

        if (isConnectionOk(connectivityState)) {
            VersionResponse versionResponse = serviceStatusBlockingStub.getVersion(Empty.getDefaultInstance());
            return ServiceStatus.builder()
                    .host(serviceStatusBlockingStub.getChannel().authority())
                    .status(connectivityState.name())
                    .artifact(versionResponse.getArtifact())
                    .name(versionResponse.getName())
                    .group(versionResponse.getGroup())
                    .version(versionResponse.getVersion())
                    .build();
        }

        return ServiceStatus.builder()
                .host(serviceStatusBlockingStub.getChannel().authority())
                .status(connectivityState.name())
                .build();
    }

    private boolean isConnectionOk(ConnectivityState connectivityState) {
        return !connectivityState.equals(ConnectivityState.SHUTDOWN) && !connectivityState.equals(ConnectivityState.TRANSIENT_FAILURE);
    }
}
