package ru.tinkoff.academy.account;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.user.UserService;

@Component
@RequiredArgsConstructor
public class AccountMapperSetup {
    private final AccountMapper accountMapper;
    private final UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void setUserService() {
        accountMapper.setUserService(userService);
    }
}
