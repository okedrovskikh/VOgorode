package ru.tinkoff.academy.rancher.garden.report;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.garden.report.GardenReportResponse;
import ru.tinkoff.academy.proto.garden.report.GardenReportServiceGrpc;

import java.util.Iterator;

@Component
public class GardenGrpcClient {
    @GrpcClient("RancherService")
    private GardenReportServiceGrpc.GardenReportServiceBlockingStub gardenReportServiceBlockingStub;

    public Iterator<GardenReportResponse> formReport() {
        return gardenReportServiceBlockingStub.formReport(Empty.getDefaultInstance());
    }
}
