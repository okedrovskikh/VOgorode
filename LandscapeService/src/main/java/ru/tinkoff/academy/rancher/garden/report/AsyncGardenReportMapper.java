package ru.tinkoff.academy.rancher.garden.report;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import ru.tinkoff.academy.proto.garden.report.GardenReportResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

@Mapper(componentModel = "spring")
public class AsyncGardenReportMapper {
    @Autowired
    private GardenReportMapper gardenReportMapper;

    @Async
    public Future<List<GardenReport>> formGardenReportList(Iterable<GardenReportResponse> gardenReportResponse) {
        return CompletableFuture.completedFuture(StreamSupport.stream(gardenReportResponse.spliterator(), false)
                .map(gardenReportMapper::mapFromGrpcResponse)
                .toList());
    }
}
