package ru.tinkoff.academy.user;

import com.google.protobuf.ByteString;
import com.google.rpc.Code;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.bank.account.BankAccount;
import ru.tinkoff.academy.proto.handyman.user.BankAccountResponse;
import ru.tinkoff.academy.proto.handyman.user.PaymentSystem;
import ru.tinkoff.academy.proto.handyman.user.UserRequest;
import ru.tinkoff.academy.proto.handyman.user.UserResponse;
import ru.tinkoff.academy.proto.handyman.user.UserResponseQuote;
import ru.tinkoff.academy.proto.handyman.user.UserServiceGrpc;
import ru.tinkoff.academy.proto.handyman.user.WorkEnum;

import java.util.Arrays;

@GrpcService
@RequiredArgsConstructor
public class UserGrpcServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    private final UserService userService;

    @Override
    public StreamObserver<UserRequest> findAllByEmailAndTelephone(StreamObserver<UserResponseQuote> responseObserver) {
        return new UserRequestObserver(userService, responseObserver);
    }

    private record UserRequestObserver(UserService userService,
                                       StreamObserver<UserResponseQuote> responseObserver) implements StreamObserver<UserRequest> {

        @Override
        public void onNext(UserRequest value) {
            try {
                User user = userService.getByEmailAndTelephone(value.getEmail(), value.getTelephone());
                responseObserver.onNext(UserResponseQuote.newBuilder().setResponse(userToUserResponse(user)).build());
            } catch (Throwable t) {
                onError(t);
            }
        }

        @Override
        public void onError(Throwable t) {
            if (t instanceof EntityNotFoundException) {
                responseObserver.onNext(UserResponseQuote.newBuilder()
                        .setError(com.google.rpc.Status.newBuilder().setCode(Code.NOT_FOUND.getNumber())
                                .setMessage(t.getMessage()).build()).build());
            } else {
                responseObserver.onNext(UserResponseQuote.newBuilder()
                        .setError(com.google.rpc.Status.newBuilder().setCode(Code.INTERNAL.getNumber())
                                .setMessage(t.getMessage()).build()).build());
            }
        }

        @Override
        public void onCompleted() {
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
                    .setPhoto(ByteString.copyFrom(mapWrapperClassToPrimitive(user.getPhoto())))
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

        private byte[] mapWrapperClassToPrimitive(Byte[] bytes) {
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
}
