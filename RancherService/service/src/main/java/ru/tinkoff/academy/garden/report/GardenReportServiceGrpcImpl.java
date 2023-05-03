package ru.tinkoff.academy.garden.report;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.garden.Garden;
import ru.tinkoff.academy.garden.GardenService;
import ru.tinkoff.academy.proto.garden.report.GardenReportResponse;
import ru.tinkoff.academy.proto.garden.report.GardenReportServiceGrpc;
import ru.tinkoff.academy.proto.work.WorkEnum;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class GardenReportServiceGrpcImpl extends GardenReportServiceGrpc.GardenReportServiceImplBase {
    private final GardenService gardenService;

    @Override
    public void formReport(Empty request, StreamObserver<GardenReportResponse> responseObserver) {
        gardenService.findAll().stream()
                .map(Garden::getWorks)
                .map(this::mapToGrpcWorkEnum)
                .map(this::buildResponse)
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    private List<WorkEnum> mapToGrpcWorkEnum(List<ru.tinkoff.academy.work.WorkEnum> works) {
        return works.stream()
                .map(work -> WorkEnum.valueOf(work.name()))
                .toList();
    }

    private GardenReportResponse buildResponse(List<WorkEnum> works) {
        return GardenReportResponse.newBuilder()
                .addAllWorks(works)
                .build();
    }
}
