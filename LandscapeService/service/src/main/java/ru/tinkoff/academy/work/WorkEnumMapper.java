package ru.tinkoff.academy.work;

import org.springframework.stereotype.Component;

@Component
public class WorkEnumMapper {
    public WorkEnum fromGrpcEnum(ru.tinkoff.academy.proto.work.WorkEnum workEnum) {
        return WorkEnum.valueOf(workEnum.name());
    }
}
