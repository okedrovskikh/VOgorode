package ru.tinkoff.academy.fielder;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.configuration.test.GrpcTestConfiguration;
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
                .addAllFields(List.of())
                .build();

        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("email2@email.com")
                .build();

        FielderResponse actualResponse = fielderServiceBlockingStub.getByEmailOrTelephone(request);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetWithIncorrectRequest() {
        FielderRequest request = FielderRequest.newBuilder()
                .build();

        fielderServiceBlockingStub.getByEmailOrTelephone(request);
    }

    @Test
    public void testGetByEmailAndTelephoneExist() {
        // 890-900-678
        FielderResponse expectedResponse = FielderResponse.newBuilder()
                .setId(6L)
                .setName("name6")
                .setSurname("surname6")
                .setEmail("email6@email.com")
                .setTelephone("890-900-678")
                .addAllFields(List.of())
                .build();

        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("email6@email.com")
                .setTelephone("890-900-678")
                .build();

        FielderResponse actualResponse = fielderServiceBlockingStub.getByEmailOrTelephone(request);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetByEmailExist() {
        FielderResponse expectedResponse = FielderResponse.newBuilder()
                .setId(6L)
                .setName("name3")
                .setSurname("surname3")
                .setEmail("email3@email.com")
                .addAllFields(List.of())
                .build();

        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("email3@email.com")
                .build();

        FielderResponse actualResponse = fielderServiceBlockingStub.getByEmailOrTelephone(request);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetByEmailAndTelephoneNotExist() {
        FielderRequest request = FielderRequest.newBuilder()
                .setEmail("notexistemail@email.com")
                .setTelephone("7897878709-989")
                .build();

        fielderServiceBlockingStub.getByEmailOrTelephone(request);
    }
}
