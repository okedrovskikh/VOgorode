package ru.tinkoff.academy.statistics.handyman;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.proto.account.BankAccountResponse;
import ru.tinkoff.academy.handyman.UserGrpcService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HandymanStatisticsService {
    private final UserGrpcService userGrpcService;

    public List<String> findAllBanks() {
        return userGrpcService.findAllBanks().stream()
                .map(BankAccountResponse::getBank).toList();
    }
}
