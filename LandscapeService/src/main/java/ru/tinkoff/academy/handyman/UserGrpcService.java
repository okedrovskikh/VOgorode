package ru.tinkoff.academy.handyman;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.handyman.user.UserRequest;
import ru.tinkoff.academy.proto.handyman.user.UserResponse;
import ru.tinkoff.academy.proto.handyman.user.UserResponseQuote;
import ru.tinkoff.academy.proto.handyman.user.UserServiceGrpc;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserGrpcService {
    @GrpcClient("HandymanClient")
    private UserServiceGrpc.UserServiceStub userServiceStub;

    public List<UserResponse> findAllByEmailAndTelephone(List<UserRequest> requests) {
        List<UserResponse> response = new ArrayList<>();
        StreamObserver<UserRequest> requestObserver = userServiceStub.findAllByEmailAndTelephone(new UserStreamObserver(response));

        for (UserRequest request : requests) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();

        return response;
    }

    private record UserStreamObserver(List<UserResponse> response) implements StreamObserver<UserResponseQuote> {

        @Override
        public void onNext(UserResponseQuote value) {
            if (value.hasResponse()) {
                response.add(value.getResponse());
            }
        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onCompleted() {

        }
    }
}
