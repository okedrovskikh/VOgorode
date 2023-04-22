package ru.tinkoff.academy.rancher.field;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.field.FieldServiceGrpc;
import ru.tinkoff.academy.proto.field.SplitValueRequest;
import ru.tinkoff.academy.proto.field.SplitValueResponseQuote;

import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class FieldGrpcService {
    @GrpcClient("RancherService")
    private FieldServiceGrpc.FieldServiceBlockingStub fieldServiceBlockingStub;

    public List<SplitValueResponseQuote> getAreasStatBySplitValue(SplitValueRequest request) {
        Iterator<SplitValueResponseQuote> responseQuoteIterator = fieldServiceBlockingStub.getAreasStatBySplitValue(request);
        Iterable<SplitValueResponseQuote> responseQuoteIterable = () -> responseQuoteIterator;
        return StreamSupport.stream(responseQuoteIterable.spliterator(), false).toList();
    }
}
