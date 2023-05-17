package ru.tinkoff.academy.landscape.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.landscape.account.dto.AccountCreateDto;
import ru.tinkoff.academy.landscape.account.type.AccountType;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;

@Mapper(componentModel = "spring", imports = {AccountType.class})
public interface AccountMapper {
    @Mapping(target = "type", expression = "java(AccountType.handyman)")
    AccountCreateDto workerCreateDtoToUserCreateDto(WorkerCreateDto workerCreateDto);
}
