package ru.tinkoff.academy.user;

import com.google.protobuf.ByteString;
import com.google.rpc.Code;
import com.google.rpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.configuration.test.GrpcTestConfiguration;
import ru.tinkoff.academy.proto.handyman.user.UserRequest;
import ru.tinkoff.academy.proto.handyman.user.UserResponse;
import ru.tinkoff.academy.proto.handyman.user.UserResponseQuote;
import ru.tinkoff.academy.proto.handyman.user.UserServiceGrpc;
import ru.tinkoff.academy.proto.handyman.user.WorkEnum;

import java.util.List;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.inProcess.address=in-process:test"
})
@Import(GrpcTestConfiguration.class)
@DirtiesContext
public class UserGrpcServiceImplTest extends AbstractIntegrationTest {
    @GrpcClient("inProcess")
    private UserServiceGrpc.UserServiceStub userServiceStub;

    @Test
    public void testWithCorrectRequest() {
        List<UserResponse> expectedResponse = List.of(
                UserResponse.newBuilder()
                        .setId(2L)
                        .setName("user2")
                        .setSurname("surname2")
                        .addSkills(WorkEnum.water)
                        .setEmail("email2@email.com")
                        .setTelephone("telephone")
                        .setPhoto(ByteString.EMPTY)
                        .build()
        );

        UserRequest request = UserRequest.newBuilder()
                        .setEmail("email2@email.com")
                        .setTelephone("telephone")
                        .build();

        StreamObserver<UserRequest> requestObserver = userServiceStub
                .findAllByEmailAndTelephone(new FielderResponseObserver(expectedResponse, List.of()));

        requestObserver.onNext(request);
        requestObserver.onCompleted();
    }

    @Test
    public void testWithNoExistByEmailOrTelephone() {
        List<Status> expectedErrors = List.of(
                Status.newBuilder().setCode(Code.NOT_FOUND.getNumber())
                        .setMessage("User wasn't find by email=email or telephone=tel").build()
        );
        UserRequest request = UserRequest.newBuilder()
                        .setEmail("email")
                        .setTelephone("tel")
                .build();

        StreamObserver<UserRequest> requestObserver = userServiceStub
                .findAllByEmailAndTelephone(new FielderResponseObserver(List.of(), expectedErrors));

        requestObserver.onNext(request);
        requestObserver.onCompleted();
    }

    private record FielderResponseObserver(List<UserResponse> expectedResponses,
                                           List<Status> expectedErrors) implements StreamObserver<UserResponseQuote> {

        @Override
        public void onNext(UserResponseQuote value) {
            if (value.hasResponse()) {
                Assertions.assertTrue(expectedResponses.contains(value.getResponse()));
            } else {
                Assertions.assertTrue(expectedErrors.contains(value.getError()));
            }
        }

        @Override
        public void onError(Throwable t) {
            Assertions.fail(t.getMessage());
        }

        @Override
        public void onCompleted() {

        }
    }
}
