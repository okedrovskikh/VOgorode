package ru.tinkoff.academy.handyman;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.handyman.user.UserRequest;
import ru.tinkoff.academy.proto.handyman.user.UserResponse;
import ru.tinkoff.academy.proto.handyman.user.UserServiceGrpc;

import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class UserGrpcService {
    @GrpcClient("HandymanClient")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public List<UserResponse> findAllByEmailOrTelephone(UserRequest request) {
        Iterator<UserResponse> responseIterator = userServiceBlockingStub.findAllEmailAndTelephone(request);
        Iterable<UserResponse> response = () -> responseIterator;
        return StreamSupport.stream(response.spliterator(), false).toList();
    }
}
