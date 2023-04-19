package ru.tinkoff.academy.account;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.account.dto.AccountUpdateDto;
import ru.tinkoff.academy.account.dto.AccountCreateDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Timed(
        value = "business.request.duration",
        extraTags = {"process", "user"},
        description = "Duration Landscape service User process handling",
        histogram = true
)
public class AccountController {
    private final AccountService accountService;

    @PostMapping("")
    public Account save(@RequestBody AccountCreateDto accountCreateDto) {
        return accountService.save(accountCreateDto);
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable("id") UUID id) {
        return accountService.getById(id);
    }

    @GetMapping("/all")
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping("")
    public List<Account> findAllById(@RequestParam("ids") List<UUID> ids) {
        return accountService.findAllById(ids);
    }

    @PutMapping("")
    public Account update(@RequestBody AccountUpdateDto accountUpdateDto) {
        return accountService.update(accountUpdateDto);
    }

    @PutMapping("/{id}")
    public Account setLatitudeAndLongitude(@PathVariable("id") UUID id, @RequestParam(name = "lat") String latitude,
                                           @RequestParam("lon") String longitude) {
        return accountService.updateLatitudeAndLongitude(id, latitude, longitude);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        this.accountService.delete(id);
    }
}
