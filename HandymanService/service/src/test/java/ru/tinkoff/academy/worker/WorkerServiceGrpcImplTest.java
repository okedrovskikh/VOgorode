package ru.tinkoff.academy.worker;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.configuration.test.GrpcTestConfiguration;
import ru.tinkoff.academy.proto.work.WorkEnum;
import ru.tinkoff.academy.proto.worker.WorkerResponse;
import ru.tinkoff.academy.proto.worker.WorkerServiceGrpc;

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
public class WorkerServiceGrpcImplTest extends AbstractIntegrationTest {
    @GrpcClient("inProcess")
    private WorkerServiceGrpc.WorkerServiceBlockingStub workerServiceBlockingStub;

    @Test
    public void testFindAll() {
        List<WorkerResponse> expectedResponse = List.of(
            WorkerResponse.newBuilder()
                    .setId("id1")
                    .setLandscapeId("18481034-3765-4ba1-9640-b5f440300299")
                    .setLatitude(800)
                    .setLongitude(800)
                    .addServices(WorkEnum.plant)
                    .build()
        );

        Iterator<WorkerResponse> workerResponseIterator = workerServiceBlockingStub.findAll(Empty.getDefaultInstance());
        Iterable<WorkerResponse> workerResponses = () -> workerResponseIterator;
        List<WorkerResponse> actualResponse = StreamSupport.stream(workerResponses.spliterator(), false).toList();

        Assertions.assertTrue(actualResponse.containsAll(expectedResponse));
    }
}
