package ru.tinkoff.academy.statistics.handyman.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.handyman.bank.BankGrpcClient;
import ru.tinkoff.academy.proto.bank.BankResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountStatisticsService {
    private final BankGrpcClient bankGrpcClient;

    public List<String> findAllBanks() {
        return bankGrpcClient.findAll().stream()
                .map(BankResponse::getBank).toList();
    }
}
