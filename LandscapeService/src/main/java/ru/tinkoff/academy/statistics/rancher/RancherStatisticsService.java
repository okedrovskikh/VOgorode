package ru.tinkoff.academy.statistics.rancher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.user.User;
import ru.tinkoff.academy.user.UserService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RancherStatisticsService {
    private final UserService userService;

    public Map<String, Double> getUsersMaxArea(Double spliter) {
        List<User> user = userService.findAll();
        // todo go to rancher and find by email + telephone fielder and get it
        // todo get max area field
        return Map.of();
    }

    public Map<String, Double> getUsersMinArea(Double spliter) {
        return Map.of();
    }

    public Map<String, Double> getUserAverageArea(Double spliter) {
        return Map.of();
    }
}
