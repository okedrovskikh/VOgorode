package ru.tinkoff.academy.bank.account;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.proto.account.BankAccountResponse;
import ru.tinkoff.academy.proto.account.BankAccountServiceGrpc;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class BankAccountGrpcServiceImpl extends BankAccountServiceGrpc.BankAccountServiceImplBase {
    private final BankAccountService bankAccountService;

    @Override
    public void findAllBanks(Empty request, StreamObserver<BankAccountResponse> responseObserver) {
        List<String> accounts = bankAccountService.findAllBanks();
        accounts.stream().map(this::createResponse).forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    private BankAccountResponse createResponse(String bank) {
        return BankAccountResponse.newBuilder()
                .setBank(bank)
                .build();
    }
}
