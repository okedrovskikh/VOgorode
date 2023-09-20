package ru.tinkoff.academy.work;

import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.work.WorkEnum;

@Component
public class WorkEnumMapper {

    public WorkEnum toGrpcWorkEnum(ru.tinkoff.academy.work.WorkEnum workEnum) {
        return WorkEnum.valueOf(workEnum.name());
    }

    public ru.tinkoff.academy.work.WorkEnum fromGrpcWorkEnum(WorkEnum workEnum) {
        return ru.tinkoff.academy.work.WorkEnum.valueOf(workEnum.name());
    }
}
