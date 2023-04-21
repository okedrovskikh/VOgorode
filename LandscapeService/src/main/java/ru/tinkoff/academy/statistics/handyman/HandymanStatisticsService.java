package ru.tinkoff.academy.statistics.handyman;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.account.Account;
import ru.tinkoff.academy.account.AccountService;
import ru.tinkoff.academy.account.type.AccountType;
import ru.tinkoff.academy.proto.handyman.user.BankAccountResponse;
import ru.tinkoff.academy.proto.handyman.user.UserRequest;
import ru.tinkoff.academy.proto.handyman.user.UserResponse;
import ru.tinkoff.academy.handyman.UserGrpcService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HandymanStatisticsService {
    private final AccountService accountService;
    private final UserGrpcService userGrpcService;

    public List<String> findAllBanks() {
        List<Account> accounts = accountService.findAllByType(AccountType.handyman);
        List<UserRequest> requests = accounts.stream().map(this::createRequest).toList();
        List<UserResponse> response = userGrpcService.findAllByEmailAndTelephone(requests);
        return response.stream().map(UserResponse::getAccountsList)
                .flatMap(Collection::stream)
                .map(BankAccountResponse::getBank)
                .collect(Collectors.toSet()).stream().toList();
    }

    private UserRequest createRequest(Account account) {
        return UserRequest.newBuilder()
                .setEmail(account.getEmail())
                .setTelephone(account.getTelephone())
                .build();
    }
}
