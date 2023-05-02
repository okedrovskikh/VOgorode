package ru.tinkoff.academy.handyman.bank;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.bank.BankResponse;
import ru.tinkoff.academy.proto.bank.BankServiceGrpc;

import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class BankGrpcClient {
    @GrpcClient("HandymanService")
    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;

    public List<BankResponse> findAll() {
        Iterator<BankResponse> responseIter = bankServiceBlockingStub.findAll(Empty.getDefaultInstance());
        Iterable<BankResponse> responseIterable = () -> responseIter;
        return StreamSupport.stream(responseIterable.spliterator(), false).toList();
    }
}
