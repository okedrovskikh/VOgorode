package ru.tinkoff.academy.statistics.rancher.area;

import org.mapstruct.Mapper;
import ru.tinkoff.academy.proto.area.AreaStat;

@Mapper(componentModel = "spring")
public interface AreaStatisticsMapper {
    AreaStatisticsResponse mapToAreaStatisticsResponse(AreaStat splitValueResponse);
}
