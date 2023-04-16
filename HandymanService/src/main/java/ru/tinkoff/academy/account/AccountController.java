package ru.tinkoff.academy.account;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.account.dto.AccountCreateDto;
import ru.tinkoff.academy.account.dto.AccountUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("")
    public Account save(@RequestBody AccountCreateDto createDto) {
        return accountService.save(createDto);
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable("id") Long id) {
        return accountService.getById(id);
    }

    @GetMapping("")
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @PutMapping("")
    public Account update(@RequestBody AccountUpdateDto updateDto) {
        return accountService.update(updateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        accountService.delete(id);
    }
}
