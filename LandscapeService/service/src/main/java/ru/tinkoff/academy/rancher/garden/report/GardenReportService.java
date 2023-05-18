package ru.tinkoff.academy.rancher.garden.report;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.proto.garden.report.GardenReportResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GardenReportService {
    private final GardenGrpcClient gardenGrpcClient;
    private final GardenReportMapper gardenReportMapper;

    @Async
    public Future<List<GardenReport>> formGardenReportList() {
        Iterable<GardenReportResponse> gardenReportResponse = gardenGrpcClient::formReport;
        return CompletableFuture.completedFuture(StreamSupport.stream(gardenReportResponse.spliterator(), false)
                .map(gardenReportMapper::mapFromGrpcResponse)
                .toList());
    }
}
