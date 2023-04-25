package ru.tinkoff.academy.bank.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.bank.account.dto.BankAccountCreateDto;
import ru.tinkoff.academy.bank.account.dto.BankAccountUpdateDto;

@Mapper(componentModel = "spring")
public abstract class BankAccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract BankAccount dtoToAccount(BankAccountCreateDto createDto);

    public BankAccount updateAccount(BankAccount bankAccount, BankAccountUpdateDto updateDto) {
        bankAccount.setCardId(updateDto.getCardId());
        bankAccount.setPaymentSystem(updateDto.getPaymentSystem());
        return bankAccount;
    }
}
