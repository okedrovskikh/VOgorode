package ru.tinkoff.academy.statistics.rancher;

import com.google.protobuf.StringValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.account.Account;
import ru.tinkoff.academy.account.AccountService;
import ru.tinkoff.academy.account.type.AccountType;
import ru.tinkoff.academy.proto.field.SplitValueRequest;
import ru.tinkoff.academy.proto.field.SplitValueResponse;
import ru.tinkoff.academy.proto.field.SplitValueResponseQuote;
import ru.tinkoff.academy.proto.fielder.FielderRequest;
import ru.tinkoff.academy.proto.fielder.FielderResponse;
import ru.tinkoff.academy.rancher.field.FieldGrpcService;
import ru.tinkoff.academy.rancher.fielder.FielderGrpcService;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RancherStatisticsService {
    private final AccountService accountService;
    private final AreaStatisticsMapper areaStatisticsMapper;
    private final FielderGrpcService fielderGrpcService;
    private final FieldGrpcService fieldServiceGrpc;

    public Map<String, AreaStatisticsResponse> getAreasStatSplit(Double splitValue) {
        return doActionBasedOnSplitValue(splitValue);
    }

    private Map<String, AreaStatisticsResponse> doActionBasedOnSplitValue(Double splitValue) {
        int compareResult = Double.compare(splitValue, 0);

        if (compareResult > 0) {
            return getAreasStatSplitBySplitValue(splitValue);
        } else if (compareResult == 0) {
            List<Account> accounts = accountService.findAllByType(AccountType.rancher);
            return getAreasStatSplitByLogin(accounts);
        }

        throw new IllegalStateException("Split value cannot be negative");
    }

    private Map<String, AreaStatisticsResponse> getAreasStatSplitBySplitValue(Double splitValue) {
        List<SplitValueResponse> response = fieldServiceGrpc.getAreasStatBySplitValue(SplitValueRequest.newBuilder()
                .setSplitValue(splitValue).build()).stream().filter(SplitValueResponseQuote::hasResponse)
                .map(SplitValueResponseQuote::getResponse)
                .toList();
        return response.stream().collect(Collectors.toMap(SplitValueResponse::getSplitValueRange, areaStatisticsMapper::mapToAreaStatisticsResponse));
    }

    private Map<String, AreaStatisticsResponse> getAreasStatSplitByLogin(List<Account> accounts) {
        Map<String, AreaStatisticsResponse> areaStatSplitByLogin = new IdentityHashMap<>();

        for (Account account : accounts) {
            FielderResponse response = fielderGrpcService.getByEmailAndTelephone(mapAccountToFielderRequest(account));
            areaStatSplitByLogin.put(account.getLogin(), areaStatisticsMapper.mapToAreaStatisticsResponse(response));
        }

        return areaStatSplitByLogin;
    }

    private FielderRequest mapAccountToFielderRequest(Account account) {
        return FielderRequest.newBuilder()
                .setEmail(account.getEmail())
                .setTelephone(StringValue.of(account.getTelephone()))
                .build();
    }
}
