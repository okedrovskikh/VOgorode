package ru.tinkoff.academy.statistics.rancher;

import org.mapstruct.Mapper;
import ru.tinkoff.academy.proto.field.SplitValueResponse;
import ru.tinkoff.academy.proto.fielder.FielderResponse;

@Mapper(componentModel = "spring")
public interface AreaStatisticsMapper {
    AreaStatisticsResponse mapToAreaStatisticsResponse(FielderResponse fielderResponse);

    AreaStatisticsResponse mapToAreaStatisticsResponse(SplitValueResponse splitValueResponse);
}
