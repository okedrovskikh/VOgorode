package ru.tinkoff.academy.bank.account;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.configuration.test.GrpcTestConfiguration;
import ru.tinkoff.academy.proto.bank.account.BankAccountResponse;
import ru.tinkoff.academy.proto.bank.account.BankAccountServiceGrpc;

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
public class BankAccountGrpcServiceImplTest extends AbstractIntegrationTest {
    @GrpcClient("inProcess")
    private BankAccountServiceGrpc.BankAccountServiceBlockingStub bankAccountServiceBlockingStub;

    @Test
    public void testWithCorrectRequest() {
        List<BankAccountResponse> expectedResponses = List.of(
                BankAccountResponse.newBuilder()
                        .setBank("bank1")
                        .build()
        );

        Iterator<BankAccountResponse> actualResponseIter = bankAccountServiceBlockingStub.findAllBanks(Empty.getDefaultInstance());
        Iterable<BankAccountResponse> actualResponseIterable = () -> actualResponseIter;
        List<BankAccountResponse> actualResponse = StreamSupport.stream(actualResponseIterable.spliterator(), false).toList();

        Assertions.assertEquals(expectedResponses, actualResponse);
    }
}
