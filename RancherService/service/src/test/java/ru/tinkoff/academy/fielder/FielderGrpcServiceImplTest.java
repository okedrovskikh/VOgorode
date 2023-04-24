package ru.tinkoff.academy.fielder;

import com.google.protobuf.StringValue;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.configuration.test.GrpcTestConfiguration;
import ru.tinkoff.academy.proto.fielder.FielderRequest;
import ru.tinkoff.academy.proto.fielder.FielderResponse;
import ru.tinkoff.academy.proto.fielder.FielderServiceGrpc;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.inProcess.address=in-process:test"
})
@Import(GrpcTestConfiguration.class)
@DirtiesContext
public class FielderGrpcServiceImplTest extends AbstractIntegrationTest {
    @GrpcClient("inProcess")
    private FielderServiceGrpc.FielderServiceBlockingStub fielderServiceBlockingStub;

    @Test
    public void testGetByEmailExist() {
        FielderResponse expectedResponse = FielderResponse.newBuilder()
                .setMax(25)
                .setAverage(25)
                .setMin(25)
                .build();

        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("email2@email.com")
                .build();

        FielderResponse actualResponse = fielderServiceBlockingStub.getAreasStatByEmailAndTelephone(request);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetByEmailAndTelephoneExist() {
        FielderResponse expectedResponse = FielderResponse.newBuilder()
                .setMax(25)
                .setAverage(25)
                .setMin(25)
                .build();

        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("email3@email.com")
                .setTelephone(StringValue.of("800-800-800"))
                .build();

        FielderResponse actualResponse = fielderServiceBlockingStub.getAreasStatByEmailAndTelephone(request);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetByEmailAndTelephoneExistWithoutFields() {
        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("email6@email.com")
                .setTelephone(StringValue.of("890-900-678"))
                .build();

        Assertions.assertThrows(StatusRuntimeException.class,
                () -> fielderServiceBlockingStub.getAreasStatByEmailAndTelephone(request),
                "Fielder by email=email6@email.com, telephone=890-900-678 doesn't have fields"
        );
    }

    @Test
    public void testGetByEmailAndTelephoneNotExist() {
        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("notexistemail@email.com")
                .setTelephone(StringValue.of("7897878709-989"))
                .build();

        Assertions.assertThrows(StatusRuntimeException.class,
                () -> fielderServiceBlockingStub.getAreasStatByEmailAndTelephone(request),
                "Fielder by email=notexistemail@email.com, telephone=7897878709-989 doesn't have fields"
        );
    }

    @Test
    public void testGetByEmailNotExists() {
        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("notexistemail@email.com")
                .build();

        Assertions.assertThrows(StatusRuntimeException.class,
                () -> fielderServiceBlockingStub.getAreasStatByEmailAndTelephone(request),
                "Fielder by email=notexistemail@email.com, telephone=null doesn't have fields"
        );
    }
}
