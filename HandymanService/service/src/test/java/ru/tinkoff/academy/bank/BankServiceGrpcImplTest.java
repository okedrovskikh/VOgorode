package ru.tinkoff.academy.bank;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.configuration.test.GrpcTestConfiguration;
import ru.tinkoff.academy.proto.bank.BankResponse;
import ru.tinkoff.academy.proto.bank.BankServiceGrpc;

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
public class BankServiceGrpcImplTest extends AbstractIntegrationTest {
    @GrpcClient("inProcess")
    private BankServiceGrpc.BankServiceBlockingStub bankAccountServiceBlockingStub;

    @Test
    public void testWithCorrectRequest() {
        List<BankResponse> expectedResponses = List.of(
                BankResponse.newBuilder()
                        .setBank("bank1")
                        .build()
        );

        Iterator<BankResponse> actualResponseIter = bankAccountServiceBlockingStub.findAll(Empty.getDefaultInstance());
        Iterable<BankResponse> actualResponseIterable = () -> actualResponseIter;
        List<BankResponse> actualResponse = StreamSupport.stream(actualResponseIterable.spliterator(), false).toList();

        Assertions.assertEquals(expectedResponses, actualResponse);
    }
}
