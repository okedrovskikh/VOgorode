package ru.tinkoff.academy.account;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.account.dto.AccountCreateDto;
import ru.tinkoff.academy.account.dto.AccountUpdateDto;
import ru.tinkoff.academy.account.type.AccountType;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    public Account save(AccountCreateDto accountCreateDto) {
        Account account = accountMapper.dtoToAccount(accountCreateDto);
        return accountRepository.save(account);
    }

    public Account getById(UUID id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User wasn't find by id: %s", id)));
    }

    public Optional<Account> findById(UUID id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByEmailAndTelephone(String email, String telephone) {
        return accountRepository.findByEmailAndTelephone(email, telephone);
    }

    public Timestamp findEarliestCreationDate() {
        return accountRepository.findEarliestCreationDate();
    }

    public Timestamp findLatestCreationDate() {
        return accountRepository.findLatestCreationDate();
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public List<Account> findAllById(Iterable<UUID> ids) {
        return accountRepository.findAllById(ids);
    }

    public List<Account> findAllByType(AccountType type) {
        return accountRepository.findAllByType(type.name());
    }

    @Transactional
    public Account update(AccountUpdateDto accountUpdateDto) {
        Account account = accountRepository.getReferenceById(accountUpdateDto.getId());
        account = accountMapper.updateAccount(accountUpdateDto, account);
        return accountRepository.save(account);
    }

    public Account updateLatitudeAndLongitude(UUID id, String latitude, String longitude) {
        Account account = accountRepository.getReferenceById(id);
        account.setLatitude(Double.valueOf(latitude));
        account.setLongitude(Double.valueOf(longitude));
        return accountRepository.save(account);
    }

    public void delete(UUID id) {
        accountRepository.deleteById(id);
    }
}
