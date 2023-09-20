package ru.tinkoff.academy.garden.report;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.configuration.test.GrpcTestConfiguration;
import ru.tinkoff.academy.proto.garden.report.GardenReportResponse;
import ru.tinkoff.academy.proto.garden.report.GardenReportServiceGrpc;
import ru.tinkoff.academy.proto.work.WorkEnum;

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
public class GardenReportServiceGrpcTest extends AbstractIntegrationTest {
    @GrpcClient("inProcess")
    private GardenReportServiceGrpc.GardenReportServiceBlockingStub gardenReportServiceBlockingStub;

    @Test
    public void testFormReport() {
        List<GardenReportResponse> expectedResponses = List.of(
                GardenReportResponse.newBuilder()
                        .addAllWorks(List.of(WorkEnum.sow))
                .build()
        );

        Iterator<GardenReportResponse> gardenReportResponseIter = gardenReportServiceBlockingStub.formReport(Empty.getDefaultInstance());
        Iterable<GardenReportResponse> gardenReportResponseIterable = () -> gardenReportResponseIter;
        List<GardenReportResponse> actualResponse = StreamSupport.stream(gardenReportResponseIterable.spliterator(), false).toList();

        Assertions.assertTrue(actualResponse.containsAll(expectedResponses));
    }
}
