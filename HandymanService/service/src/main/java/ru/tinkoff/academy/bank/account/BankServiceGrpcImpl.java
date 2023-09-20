package ru.tinkoff.academy.bank.account;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.bank.account.BankAccountService;
import ru.tinkoff.academy.proto.bank.BankResponse;
import ru.tinkoff.academy.proto.bank.BankServiceGrpc;

@GrpcService
@RequiredArgsConstructor
public class BankServiceGrpcImpl extends BankServiceGrpc.BankServiceImplBase {
    private final BankAccountService bankAccountService;

    @Override
    public void findAll(Empty request, StreamObserver<BankResponse> responseObserver) {
        bankAccountService.findAllBanks().stream()
                .map(this::createResponse)
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    private BankResponse createResponse(String bank) {
        return BankResponse.newBuilder()
                .setBank(bank)
                .build();
    }
}
