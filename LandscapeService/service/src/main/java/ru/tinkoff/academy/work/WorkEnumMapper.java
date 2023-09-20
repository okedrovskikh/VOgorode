package ru.tinkoff.academy.work;

import org.springframework.stereotype.Component;

@Component
public class WorkEnumMapper {
    public ru.tinkoff.academy.proto.work.WorkEnum toGrpcEnum(WorkEnum workEnum) {
        return ru.tinkoff.academy.proto.work.WorkEnum.valueOf(workEnum.name());
    }

    public WorkEnum fromGrpcEnum(ru.tinkoff.academy.proto.work.WorkEnum workEnum) {
        return WorkEnum.valueOf(workEnum.name());
    }
}
