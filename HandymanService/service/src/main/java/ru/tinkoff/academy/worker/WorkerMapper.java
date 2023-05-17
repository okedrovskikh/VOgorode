package ru.tinkoff.academy.worker;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.academy.landscape.account.Account;
import ru.tinkoff.academy.proto.worker.WorkerResponse;
import ru.tinkoff.academy.work.WorkEnumMapper;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;

@Mapper(componentModel = "spring")
public abstract class WorkerMapper {
    @Autowired
    protected WorkEnumMapper workEnumMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "landscapeUserId", ignore = true)
    public abstract Worker dtoToWorker(WorkerCreateDto workerCreateDto);

    @Mapping(target = "landscapeUserId", ignore = true)
    public abstract Worker dtoToWorker(WorkerUpdateDto workerCreateDto);

    @Mapping(target = "id", source = "worker.id")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "latitude", source = "worker.latitude")
    @Mapping(target = "longitude", source = "worker.longitude")
    public abstract ExtendedByUserWorker toExtendedWorker(Worker worker, Account account);

    public WorkerResponse mapToGrpcResponse(Worker worker) {
        return WorkerResponse.newBuilder()
                .setId(worker.getId())
                .setLandscapeId(worker.getLandscapeUserId().toString())
                .setLatitude(worker.getLatitude())
                .setLongitude(worker.getLongitude())
                .addAllServices(worker.getServices().stream().map(workEnumMapper::toGrpcWorkEnum).toList())
                .build();
    }
}
