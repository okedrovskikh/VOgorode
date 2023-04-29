package ru.tinkoff.academy.field;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.configuration.test.GrpcTestConfiguration;
import ru.tinkoff.academy.proto.field.AreaStat;
import ru.tinkoff.academy.proto.field.AreaStatRequest;
import ru.tinkoff.academy.proto.field.AreaStatResponse;
import ru.tinkoff.academy.proto.field.FieldServiceGrpc;

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
    public void testGetAreasStatBySplitValue() {
        AreaStatResponse expectedResponse = AreaStatResponse.newBuilder()
                .addResponse(
                        AreaStat.newBuilder()
                                .setSplitValue("20 - 30")
                                .setMax(25)
                                .setAverage(25)
                                .setMin(25)
                                .build()
                ).build();

        AreaStatRequest request = AreaStatRequest.newBuilder()
                .setSplitValue(10)
                .build();

        AreaStatResponse actualResponse = fieldServiceBlockingStub.getAreasStatBySplitValue(request);

        Assertions.assertTrue(actualResponse.getResponseList().containsAll(expectedResponse.getResponseList()));
    }

    @Test
    public void testGetAreasStat() {
        AreaStatResponse expectedResponse = AreaStatResponse.newBuilder()
                .addResponse(
                        AreaStat.newBuilder()
                                .setSplitValue("email2@email.com:null")
                                .setMax(25)
                                .setAverage(25)
                                .setMin(25)
                                .build()
                ).addResponse(
                        AreaStat.newBuilder()
                                .setSplitValue("email3@email.com:800-800-800")
                                .setMax(25)
                                .setAverage(25)
                                .setMin(25)
                                .build()
                ).build();

        AreaStatResponse actualResponse = fieldServiceBlockingStub.getAreasStatSplitByEmailAndTelephone(Empty.getDefaultInstance());

        Assertions.assertTrue(actualResponse.getResponseList().containsAll(expectedResponse.getResponseList()));
    }
}
