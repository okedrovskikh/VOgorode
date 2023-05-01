package ru.tinkoff.academy.statistics.handyman.bank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.handyman.bank.account.BankAccountGrpcClient;
import ru.tinkoff.academy.proto.bank.account.BankAccountResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountStatisticsService {
    private final BankAccountGrpcClient bankAccountGrpcClient;

    public List<String> findAllBanks() {
        return bankAccountGrpcClient.findAllBanks().stream()
                .map(BankAccountResponse::getBank).toList();
    }
}
