package ru.tinkoff.academy.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.account.dto.AccountCreateDto;
import ru.tinkoff.academy.account.dto.AccountUpdateDto;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {
    @Mapping(target = "id", ignore = true)
    public abstract Account dtoToAccount(AccountCreateDto createDto);

    public Account updateAccount(Account account, AccountUpdateDto updateDto) {
        account.setCardId(updateDto.getCardId());
        account.setPaymentSystem(updateDto.getPaymentSystem());
        return account;
    }
}
