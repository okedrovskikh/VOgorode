package ru.tinkoff.academy.statistics.handyman.bank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.handyman.bank.account.BankAccountGrpcService;
import ru.tinkoff.academy.proto.bank.account.BankAccountResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountStatisticsService {
    private final BankAccountGrpcService bankAccountGrpcService;

    public List<String> findAllBanks() {
        return bankAccountGrpcService.findAllBanks().stream()
                .map(BankAccountResponse::getBank).toList();
    }
}
