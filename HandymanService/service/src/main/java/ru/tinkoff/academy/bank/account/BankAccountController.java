package ru.tinkoff.academy.bank.account;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.bank.account.dto.BankAccountCreateDto;
import ru.tinkoff.academy.bank.account.dto.BankAccountUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Timed(
        value = "business.request.duration",
        extraTags = {"process", "account"},
        description = "Duration Handyman service Account process handling",
        histogram = true
)
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @PostMapping("")
    public BankAccount save(@RequestBody BankAccountCreateDto createDto) {
        return bankAccountService.save(createDto);
    }

    @GetMapping("/{id}")
    public BankAccount getById(@PathVariable("id") Long id) {
        return bankAccountService.getById(id);
    }

    @GetMapping("/all")
    public List<BankAccount> findAll() {
        return bankAccountService.findAll();
    }

    @PutMapping("")
    public BankAccount update(@RequestBody BankAccountUpdateDto updateDto) {
        return bankAccountService.update(updateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        bankAccountService.delete(id);
    }
}
