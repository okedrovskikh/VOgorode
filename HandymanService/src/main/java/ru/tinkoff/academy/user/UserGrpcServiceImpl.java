package ru.tinkoff.academy.user;

import com.google.protobuf.ByteString;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.bank.account.BankAccount;
import ru.tinkoff.academy.proto.handyman.user.BankAccountResponse;
import ru.tinkoff.academy.proto.handyman.user.PaymentSystem;
import ru.tinkoff.academy.proto.handyman.user.UserRequest;
import ru.tinkoff.academy.proto.handyman.user.UserResponse;
import ru.tinkoff.academy.proto.handyman.user.UserServiceGrpc;
import ru.tinkoff.academy.proto.handyman.user.WorkEnum;

import java.util.Arrays;
import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class UserGrpcServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    private final UserService userService;

    @Override
    public void findAll(Empty request, StreamObserver<UserResponse> responseObserver) {
        List<User> users = userService.findAll(false);
        users.stream().map(this::userToUserResponse).forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void findAllEmailAndTelephone(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        List<User> users = request.getSearchRequestList().stream()
                .map(req -> userService.getByEmailOrTelephone(req.getEmail(), req.getTelephone()))
                .toList();
        users.stream().map(this::userToUserResponse).forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    private UserResponse userToUserResponse(User user) {
        return UserResponse.newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .addAllSkills(Arrays.stream(user.getSkills()).map(skill -> WorkEnum.valueOf(skill.name())).toList())
                .setEmail(user.getEmail())
                .setTelephone(user.getTelephone())
                .addAllAccounts(user.getAccounts().stream().map(this::mapBankAccountToResponse).toList())
                .setPhoto(ByteString.copyFrom(mapClassToPrimitive(user.getPhoto())))
                .build();
    }

    private BankAccountResponse mapBankAccountToResponse(BankAccount bankAccount) {
        return BankAccountResponse.newBuilder()
                .setId(bankAccount.getId())
                .setCardId(bankAccount.getCardId())
                .setPaymentSystem(PaymentSystem.valueOf(bankAccount.getPaymentSystem().name()))
                .setBank(bankAccount.getBank())
                .build();
    }

    private byte[] mapClassToPrimitive(Byte[] bytes) {
        byte[] primitiveBytes;
        if (bytes == null) {
            primitiveBytes = new byte[0];
        } else {
            primitiveBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                primitiveBytes[i] = bytes[i];
            }
        }
        return primitiveBytes;
    }
}
