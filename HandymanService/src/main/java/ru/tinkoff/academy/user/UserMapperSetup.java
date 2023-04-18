package ru.tinkoff.academy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.account.AccountService;

@Component
@RequiredArgsConstructor
public class UserMapperSetup {
    private final UserMapper userMapper;
    private final AccountService accountService;

    @EventListener(ApplicationReadyEvent.class)
    public void setAccountService() {
        userMapper.setAccountService(accountService);
    }
}
