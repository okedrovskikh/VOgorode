package ru.tinkoff.academy.rancher.garden.report;

import org.mapstruct.Mapper;
import ru.tinkoff.academy.proto.garden.report.GardenReportResponse;

@Mapper(componentModel = "spring")
public interface GardenReportMapper {
    GardenReport mapFromGrpcResponse(GardenReportResponse response);
}
