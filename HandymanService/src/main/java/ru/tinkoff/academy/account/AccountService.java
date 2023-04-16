package ru.tinkoff.academy.account;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.account.dto.AccountCreateDto;
import ru.tinkoff.academy.account.dto.AccountUpdateDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public Account save(AccountCreateDto createDto) {
        Account account = accountMapper.dtoToAccount(createDto);
        return accountRepository.save(account);
    }

    public Account getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account wasn't find by id: %s", id)));
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public List<Account> findAllByIds(Iterable<Long> ids) {
        return accountRepository.findAllById(ids);
    }

    public Account update(AccountUpdateDto updateDto) {
        Account account = accountRepository.getReferenceById(updateDto.getId());
        account = accountMapper.updateAccount(account, updateDto);
        return accountRepository.save(account);
    }

    public void delete(Long id) {
        accountRepository.deleteById(id);
    }
}
