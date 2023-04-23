package ru.tinkoff.academy.user;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.bank.account.BankAccount;
import ru.tinkoff.academy.bank.account.BankAccountService;

@Aspect
@Component
@RequiredArgsConstructor
public class UpdateBankAccountsUsersAspect {
    private final BankAccountService bankAccountService;

    @AfterReturning(pointcut = "execution(* ru.tinkoff.academy.bank.account.BankAccountMapper.updateAccount(ru.tinkoff.academy.bank.account.BankAccount, ru.tinkoff.academy.bank.account.dto.BankAccountUpdateDto)) || " +
            "execution(* ru.tinkoff.academy.bank.account.BankAccountMapper.dtoToAccount(ru.tinkoff.academy.bank.account.dto.BankAccountCreateDto))",
            returning = "user")
    public void setFieldsFielder(User user) {
        bankAccountService.updateBankAccountsUser(user.getAccounts().stream().map(BankAccount::getId).toList(), user);
    }
}
