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
import ru.tinkoff.academy.proto.rancher.fielder.FieldResponse;
import ru.tinkoff.academy.proto.rancher.fielder.FieldResponse.Point;
import ru.tinkoff.academy.proto.rancher.fielder.FielderRequest;
import ru.tinkoff.academy.proto.rancher.fielder.FielderResponse;
import ru.tinkoff.academy.proto.rancher.fielder.FielderServiceGrpc;

import java.util.List;

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
    public void testGetWithCorrectRequest() {
        FielderResponse expectedResponse = FielderResponse.newBuilder()
                .setId(2L)
                .setName("name2")
                .setSurname("surname2")
                .setEmail("email2@email.com")
                .addAllFields(List.of(
                        FieldResponse.newBuilder()
                                .setId(1)
                                .setAddress("addr1")
                                .setLatitude(800)
                                .setLongitude(800)
                                .setArea(Point.newBuilder().setX(1).setY(1).build())
                                .build()
                ))
                .build();

        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("email2@email.com")
                .build();

        FielderResponse actualResponse = fielderServiceBlockingStub.getByEmailAndTelephone(request);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetByEmailAndTelephoneExist() {
        FielderResponse expectedResponse = FielderResponse.newBuilder()
                .setId(6L)
                .setName("name6")
                .setSurname("surname6")
                .setEmail("email6@email.com")
                .setTelephone(StringValue.of("890-900-678"))
                .addAllFields(List.of())
                .build();

        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("email6@email.com")
                .setTelephone(StringValue.of("890-900-678"))
                .build();

        FielderResponse actualResponse = fielderServiceBlockingStub.getByEmailAndTelephone(request);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetByEmailExist() {
        FielderResponse expectedResponse = FielderResponse.newBuilder()
                .setId(3L)
                .setName("name3")
                .setSurname("surname3")
                .setEmail("email3@email.com")
                .addAllFields(List.of())
                .build();

        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("email3@email.com")
                .build();

        FielderResponse actualResponse = fielderServiceBlockingStub.getByEmailAndTelephone(request);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetByEmailAndTelephoneNotExist() {
        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("notexistemail@email.com")
                .setTelephone(StringValue.of("7897878709-989"))
                .build();

        Assertions.assertThrows(StatusRuntimeException.class,
                () -> fielderServiceBlockingStub.getByEmailAndTelephone(request),
                "Fielder wasn't find by email=notexistemail@email.com or telephone=7897878709-989"
        );
    }
}
