package ru.tinkoff.academy.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.bank.account.BankAccount;
import ru.tinkoff.academy.bank.account.BankAccountService;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;
    private final UserMapper userMapper;

    @Transactional
    public User save(UserCreateDto createDto) {
        User user = userMapper.dtoToUser(createDto);

        if (createDto.getAccountsId() != null) {
            List<BankAccount> accounts = bankAccountService.updateBankAccountsUser(createDto.getAccountsId(), user);
            user.setAccounts(accounts);
        }

        return userRepository.save(user);
    }

    public User getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User wasn't find by id: %s", id)));
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll(boolean sortedBySurname) {
        return sortedBySurname ? findAllSortedBySurname() : findAllUnsorted();
    }

    public List<User> findAllUnsorted() {
        return userRepository.findAll();
    }

    public List<User> findAllSortedBySurname() {
        return userRepository.findAll(Sort.by("surname"));
    }

    @Transactional
    public User update(UserUpdateDto updateDto) {
        User user = userRepository.getReferenceById(updateDto.getId());
        user = userMapper.updateUser(user, updateDto);
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
