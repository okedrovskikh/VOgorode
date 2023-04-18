package ru.tinkoff.academy.account;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.account.dto.AccountCreateDto;
import ru.tinkoff.academy.account.dto.AccountUpdateDto;
import ru.tinkoff.academy.user.UserService;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {
    @Setter
    protected UserService userService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java(userService.getById(createDto.getUserId()))")
    public abstract Account dtoToAccount(AccountCreateDto createDto);

    public Account updateAccount(Account account, AccountUpdateDto updateDto) {
        account.setCardId(updateDto.getCardId());
        account.setUser(userService.getById(updateDto.getUserId()));
        account.setPaymentSystem(updateDto.getPaymentSystem());
        return account;
    }
}
