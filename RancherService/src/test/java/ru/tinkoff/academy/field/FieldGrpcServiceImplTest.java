package ru.tinkoff.academy.field;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.configuration.test.GrpcTestConfiguration;
import ru.tinkoff.academy.proto.field.FieldServiceGrpc;
import ru.tinkoff.academy.proto.field.SplitValueRequest;
import ru.tinkoff.academy.proto.field.SplitValueResponse;
import ru.tinkoff.academy.proto.field.SplitValueResponseQuote;

import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.inProcess.address=in-process:test"
})
@Import(GrpcTestConfiguration.class)
@DirtiesContext
public class FieldGrpcServiceImplTest extends AbstractIntegrationTest {
    @GrpcClient("inProcess")
    private FieldServiceGrpc.FieldServiceBlockingStub fieldServiceBlockingStub;

    @Test
    public void testGetSplit() {
        List<SplitValueResponseQuote> expectedResponse = List.of(
                SplitValueResponseQuote.newBuilder()
                        .setResponse(SplitValueResponse.newBuilder()
                                .setSplitValueRange("20 - 30")
                                .setMax(25)
                                .setAverage(25)
                                .setMin(25)
                                .build()
                        )
                        .build()
        );

        SplitValueRequest request = SplitValueRequest.newBuilder()
                .setSplitValue(10)
                .build();

        Iterator<SplitValueResponseQuote> actualResponseIter = fieldServiceBlockingStub.getAreasStatBySplitValue(request);
        Iterable<SplitValueResponseQuote> actualResponseIterable = () -> actualResponseIter;
        List<SplitValueResponseQuote> actualResponse = StreamSupport.stream(actualResponseIterable.spliterator(), false).toList();

        Assertions.assertTrue(CollectionUtils.isEqualCollection(expectedResponse, actualResponse));
    }
}
