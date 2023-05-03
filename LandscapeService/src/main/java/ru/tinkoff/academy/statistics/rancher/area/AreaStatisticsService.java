package ru.tinkoff.academy.statistics.rancher.area;

import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.account.Account;
import ru.tinkoff.academy.account.AccountService;
import ru.tinkoff.academy.account.type.AccountType;
import ru.tinkoff.academy.exceptions.GrpcStreamErrorException;
import ru.tinkoff.academy.proto.area.AreaStat;
import ru.tinkoff.academy.proto.area.AreaStatRequest;
import ru.tinkoff.academy.proto.area.AreaStatResponse;
import ru.tinkoff.academy.rancher.area.AreaGrpcClient;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaStatisticsService {
    private final AccountService accountService;
    private final AreaStatisticsMapper areaStatisticsMapper;
    private final AreaGrpcClient fieldServiceGrpc;

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

        return getAreasStat(fieldServiceGrpc.getAreasStatBySplitValueStream(request), AreaStat::getSplitValue);
    }

    private Map<String, AreaStatisticsResponse> getAreasStatSplitByLogin() {
        Iterator<AreaStatResponse> responseStream = fieldServiceGrpc.getAreasStatSplitByEmailAndTelephoneStream();
        Map<EmailAndTelephone, String> loginByEmailAndTelephoneMap = accountService.findAllByType(AccountType.rancher)
                .stream()
                .collect(Collectors.toMap(account -> new EmailAndTelephone(account.getEmail(), account.getTelephone()), Account::getLogin));

        return getAreasStat(responseStream, stat -> getAccountLogin(stat, loginByEmailAndTelephoneMap));
    }

    private Map<String, AreaStatisticsResponse> getAreasStat(Iterator<AreaStatResponse> responseStream,
                                                             Function<AreaStat, String> splitValueKeyFunction) {
        Map<String, AreaStatisticsResponse> areasStat = new IdentityHashMap<>();

        while (responseStream.hasNext()) {
            AreaStatResponse response = responseStream.next();

            for (AreaStat stat : response.getStatsList()) {
                String splitValueKey = splitValueKeyFunction.apply(stat);
                areasStat.put(splitValueKey, areaStatisticsMapper.mapToAreaStatisticsResponse(stat));
            }
        }

        return areasStat;
    }

    private String getAccountLogin(AreaStat stat, Map<EmailAndTelephone, String> loginByEmailAndTelephoneMap) {
        String[] emailAndTelephone = stat.getSplitValue().split(":");
        return loginByEmailAndTelephoneMap.get(new EmailAndTelephone(emailAndTelephone[0], emailAndTelephone[1]));
    }

    private record EmailAndTelephone(String email, String telephone) {}
}
