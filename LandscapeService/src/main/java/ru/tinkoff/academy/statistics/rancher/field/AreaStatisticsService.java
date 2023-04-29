package ru.tinkoff.academy.statistics.rancher.field;

import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.account.Account;
import ru.tinkoff.academy.account.AccountService;
import ru.tinkoff.academy.proto.field.AreaStat;
import ru.tinkoff.academy.proto.field.AreaStatRequest;
import ru.tinkoff.academy.proto.field.AreaStatResponse;
import ru.tinkoff.academy.rancher.field.FieldGrpcService;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AreaStatisticsService {
    private final AccountService accountService;
    private final AreaStatisticsMapper areaStatisticsMapper;
    private final FieldGrpcService fieldServiceGrpc;

    public Map<String, AreaStatisticsResponse> getAreasStatSplit(Double splitValue) {
        return doActionBasedOnSplitValue(splitValue);
    }

    private Map<String, AreaStatisticsResponse> doActionBasedOnSplitValue(Double splitValue) {
        int compareResult = Double.compare(splitValue, 0);

        if (compareResult > 0) {
            return getAreasStatSplitBySplitValue(splitValue);
        } else if (compareResult == 0) {
            return getAreasStatSplitByLogin();
        }

        throw new IllegalArgumentException("Split value cannot be negative");
    }

    private Map<String, AreaStatisticsResponse> getAreasStatSplitBySplitValue(Double splitValue) {
        AreaStatRequest request = AreaStatRequest.newBuilder()
                .setSplitValue(splitValue).build();
        return getAreasStat(() -> fieldServiceGrpc.getAreasStatBySplitValue(request), AreaStat::getSplitValue);
    }

    private Map<String, AreaStatisticsResponse> getAreasStatSplitByLogin() {
        return getAreasStat(fieldServiceGrpc::getAreasStatSplitByEmailAndTelephone, this::getAccountLogin);
    }

    private Map<String, AreaStatisticsResponse> getAreasStat(Supplier<AreaStatResponse> responseSupplier,
                                                             Function<AreaStat, String> splitValueKeyFunction) {
        Map<String, AreaStatisticsResponse> areasStat = new IdentityHashMap<>();
        AreaStatResponse response = responseSupplier.get();

        for (var a : response.getResponseList()) {
            String splitValueKey = splitValueKeyFunction.apply(a);
            areasStat.put(splitValueKey, areaStatisticsMapper.mapToAreaStatisticsResponse(a));
        }

        return areasStat;
    }

    private String getAccountLogin(AreaStat stat) {
        String[] emailAndTelephone = stat.getSplitValue().split(":");
        Account account = accountService.getByEmailAndTelephone(emailAndTelephone[0], emailAndTelephone[1]);
        return account.getLogin();
    }
}
