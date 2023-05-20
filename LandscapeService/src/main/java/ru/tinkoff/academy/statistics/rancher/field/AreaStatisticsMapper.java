package ru.tinkoff.academy.statistics.rancher.field;

import org.mapstruct.Mapper;
import ru.tinkoff.academy.proto.field.AreaStat;

@Mapper(componentModel = "spring")
public interface AreaStatisticsMapper {
    AreaStatisticsResponse mapToAreaStatisticsResponse(AreaStat splitValueResponse);
}
