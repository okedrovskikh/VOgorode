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
import ru.tinkoff.academy.proto.field.AreaStats;
import ru.tinkoff.academy.proto.field.FieldServiceGrpc;

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
    public void testGetAreasStatBySplitValue() {
        AreaStatResponse expectedResponse = AreaStatResponse.newBuilder()
                .setStats(AreaStats.newBuilder()
                        .addStats(AreaStat.newBuilder()
                                .setSplitValue("20 - 30")
                                .setMax(25)
                                .setAverage(25)
                                .setMin(25)
                                .build()
                        ).build()
                ).build();

        AreaStatRequest request = AreaStatRequest.newBuilder()
                .setSplitValue(10)
                .build();

        Iterator<AreaStatResponse> actualResponseIter = fieldServiceBlockingStub.getAreasStatBySplitValue(request);
        Iterable<AreaStatResponse> actualResponseIterable = () -> actualResponseIter;
        List<AreaStatResponse> actualResponse = StreamSupport.stream(actualResponseIterable.spliterator(), false).toList();

        Assertions.assertTrue(actualResponse.get(0).getStats().getStatsList().containsAll(expectedResponse.getStats().getStatsList()));
    }

    @Test
    public void testGetAreasStat() {
        AreaStatResponse expectedResponse = AreaStatResponse.newBuilder()
                .setStats(AreaStats.newBuilder()
                        .addAllStats(List.of(
                                        AreaStat.newBuilder()
                                                .setSplitValue("email2@email.com:null")
                                                .setMax(25)
                                                .setAverage(25)
                                                .setMin(25)
                                                .build(),
                                        AreaStat.newBuilder()
                                                .setSplitValue("email3@email.com:800-800-800")
                                                .setMax(25)
                                                .setAverage(25)
                                                .setMin(25)
                                                .build()
                                )
                        ).build()
                ).build();

        Iterator<AreaStatResponse> actualResponseIter = fieldServiceBlockingStub.getAreasStatSplitByEmailAndTelephone(Empty.getDefaultInstance());
        Iterable<AreaStatResponse> actualResponseIterable = () -> actualResponseIter;
        List<AreaStatResponse> actualResponse = StreamSupport.stream(actualResponseIterable.spliterator(), false).toList();

        Assertions.assertTrue(actualResponse.get(0).getStats().getStatsList().containsAll(expectedResponse.getStats().getStatsList()));
    }
}
