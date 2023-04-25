package ru.tinkoff.academy.service.status;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.configuration.test.GrpcTestConfiguration;
import ru.tinkoff.academy.proto.service.status.ReadinessResponse;
import ru.tinkoff.academy.proto.service.status.ServiceStatusGrpc;
import ru.tinkoff.academy.proto.service.status.VersionResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.inProcess.address=in-process:test"
})
@Import(GrpcTestConfiguration.class)
@DirtiesContext
public class ServiceStatusGrpcServiceImplTests extends AbstractIntegrationTest {
    @GrpcClient("inProcess")
    private ServiceStatusGrpc.ServiceStatusBlockingStub serviceStatusBlockingStub;
    @Autowired
    private BuildProperties buildProperties;

    @Test
    public void testGetReadiness() {
        ReadinessResponse expectedReadinessResponse = ReadinessResponse.newBuilder()
                .setStatus("OK")
                .build();
        ReadinessResponse actualReadinessResponse = serviceStatusBlockingStub.getReadiness(Empty.getDefaultInstance());
        assertEquals(expectedReadinessResponse, actualReadinessResponse);
    }

    @Test
    public void testGetVersion() {
        VersionResponse expectedVersionResponse = VersionResponse.newBuilder()
                .setVersion(buildProperties.getVersion())
                .setName(buildProperties.getName())
                .setGroup(buildProperties.getGroup())
                .setArtifact(buildProperties.getArtifact())
                .build();
        VersionResponse actualVersionResponse = serviceStatusBlockingStub.getVersion(Empty.getDefaultInstance());
        assertEquals(expectedVersionResponse, actualVersionResponse);
    }
}
