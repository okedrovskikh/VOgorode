package ru.tinkoff.academy.statistics.handyman;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.user.User;
import ru.tinkoff.academy.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HandymanStatisticsService {
    private final UserService userService;

    public List<String> findAllBanks() {
        List<User> users = userService.findAll();
        // todo go to handyman, get handyman users, get accounts
        // todo get accounts banks
        return List.of();
    }

    public LocalDateTime getEarliestCreationDate() {
        return null;
    }

    public LocalDateTime getLatestCreationDate() {
        return null;
    }
}
