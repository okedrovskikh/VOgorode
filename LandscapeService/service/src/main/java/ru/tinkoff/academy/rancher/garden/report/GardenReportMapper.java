package ru.tinkoff.academy.rancher.garden.report;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.academy.proto.garden.report.GardenReportResponse;
import ru.tinkoff.academy.work.WorkEnumMapper;

@Mapper(componentModel = "spring")
public abstract class GardenReportMapper {
    @Autowired
    protected WorkEnumMapper workEnumMapper;

    @Mapping(target = "works", expression = "java(response.getWorksList().stream().map(workEnumMapper::fromGrpcEnum).toList())")
    public abstract GardenReport mapFromGrpcResponse(GardenReportResponse response);
}
