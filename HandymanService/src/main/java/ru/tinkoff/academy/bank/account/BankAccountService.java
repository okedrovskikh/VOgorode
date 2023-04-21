package ru.tinkoff.academy.bank.account;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.bank.account.dto.BankAccountCreateDto;
import ru.tinkoff.academy.bank.account.dto.BankAccountUpdateDto;

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

    public BankAccount getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account wasn't find by id: %s", id)));
    }

    public Optional<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id);
    }

    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    public List<BankAccount> findAllByIds(Iterable<Long> ids) {
        return bankAccountRepository.findAllById(ids);
    }

    @Transactional
    public BankAccount update(BankAccountUpdateDto updateDto) {
        BankAccount bankAccount = bankAccountRepository.getReferenceById(updateDto.getId());
        bankAccount = bankAccountMapper.updateAccount(bankAccount, updateDto);
        return bankAccountRepository.save(bankAccount);
    }

    public void delete(Long id) {
        bankAccountRepository.deleteById(id);
    }
}
