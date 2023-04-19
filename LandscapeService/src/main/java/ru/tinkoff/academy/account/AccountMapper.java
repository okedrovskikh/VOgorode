package ru.tinkoff.academy.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.account.dto.AccountUpdateDto;
import ru.tinkoff.academy.account.dto.AccountCreateDto;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    public abstract Account dtoToUser(AccountCreateDto accountCreateDto);

    public Account updateUser(AccountUpdateDto accountUpdateDto, Account account) {
        if (accountUpdateDto.getType() != null) {
            account.setType(accountUpdateDto.getType());
        }

        if (accountUpdateDto.getEmail() != null) {
            account.setEmail(accountUpdateDto.getEmail());
        }

        if (accountUpdateDto.getLogin() != null) {
            account.setLogin(accountUpdateDto.getLogin());
        }

        if (accountUpdateDto.getTelephone() != null) {
            account.setTelephone(accountUpdateDto.getTelephone());
        }

        if (accountUpdateDto.getLatitude() != null) {
            account.setLatitude(Double.valueOf(accountUpdateDto.getLatitude()));
        }

        if (accountUpdateDto.getLongitude() != null) {
            account.setLongitude(Double.valueOf(accountUpdateDto.getLongitude()));
        }

        return account;
    }
}
