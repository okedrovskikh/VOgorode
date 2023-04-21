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
        List<UserResponse> response = userGrpcService.findAllByEmailOrTelephone(createRequest(accounts));
        return response.stream().map(UserResponse::getAccountsList)
                .flatMap(Collection::stream)
                .map(BankAccountResponse::getBank)
                .collect(Collectors.toSet()).stream().toList();
    }

    private UserRequest createRequest(List<Account> accounts) {
        return UserRequest.newBuilder()
                .addAllSearchRequest(mapAccountsToSearchRequests(accounts))
                .build();
    }

    private Iterable<UserRequest.SearchRequest> mapAccountsToSearchRequests(List<Account> accounts) {
        return accounts.stream().map(this::mapAccountToSearchRequest).toList();
    }

    private UserRequest.SearchRequest mapAccountToSearchRequest(Account account) {
        return UserRequest.SearchRequest.newBuilder().setEmail(account.getEmail())
                .setTelephone(account.getTelephone()).build();
    }
}
