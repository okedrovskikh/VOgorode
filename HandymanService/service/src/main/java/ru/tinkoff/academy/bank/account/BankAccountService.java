package ru.tinkoff.academy.bank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.bank.account.dto.BankAccountCreateDto;
import ru.tinkoff.academy.bank.account.dto.BankAccountUpdateDto;
import ru.tinkoff.academy.exceptions.EntityNotFoundException;
import ru.tinkoff.academy.user.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    public BankAccount save(BankAccountCreateDto createDto) {
        BankAccount bankAccount = bankAccountMapper.dtoToAccount(createDto);
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount getById(String id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account wasn't find by id: %s", id)));
    }

    public Optional<BankAccount> findById(String id) {
        return bankAccountRepository.findById(id);
    }

    public List<String> findAllBanks() {
        return bankAccountRepository.findAllBanks();
    }

    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    @Transactional
    public BankAccount update(BankAccountUpdateDto updateDto) {
        BankAccount bankAccount = getById(updateDto.getId());
        bankAccount = bankAccountMapper.updateAccount(bankAccount, updateDto);
        return bankAccountRepository.save(bankAccount);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<BankAccount> updateBankAccountsUser(List<String> fieldsId, User user) {
        List<BankAccount> accounts = bankAccountRepository.findAllById(fieldsId);
        accounts.forEach(a -> a.setUser(user));
        return bankAccountRepository.saveAll(accounts);
    }

    public void delete(String id) {
        bankAccountRepository.deleteById(id);
    }
}
