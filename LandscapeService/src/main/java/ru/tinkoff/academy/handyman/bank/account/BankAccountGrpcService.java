package ru.tinkoff.academy.handyman.bank.account;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.bank.account.BankAccountResponse;
import ru.tinkoff.academy.proto.bank.account.BankAccountServiceGrpc;

import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class BankAccountGrpcService {
    @GrpcClient("HandymanService")
    private BankAccountServiceGrpc.BankAccountServiceBlockingStub bankAccountServiceBlockingStub;

    public List<BankAccountResponse> findAllBanks() {
        Iterator<BankAccountResponse> responseIter = bankAccountServiceBlockingStub.findAllBanks(Empty.getDefaultInstance());
        Iterable<BankAccountResponse> responseIterable = () -> responseIter;
        return StreamSupport.stream(responseIterable.spliterator(), false).toList();
    }
}
