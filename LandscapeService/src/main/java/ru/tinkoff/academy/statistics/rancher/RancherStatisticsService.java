package ru.tinkoff.academy.statistics.rancher;

import com.google.protobuf.StringValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.account.Account;
import ru.tinkoff.academy.account.AccountService;
import ru.tinkoff.academy.account.type.AccountType;
import ru.tinkoff.academy.proto.rancher.fielder.FieldResponse;
import ru.tinkoff.academy.proto.rancher.fielder.FielderRequest;
import ru.tinkoff.academy.proto.rancher.fielder.FielderResponse;
import ru.tinkoff.academy.rancher.FielderGrpcService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RancherStatisticsService {
    private static final String splitString = "%s - %s";

    private final AccountService accountService;
    private final FielderGrpcService fielderGrpcService;

    public Map<String, Double> getUsersMaxArea(Double splitValue) {
        return doActionBasedOnSplitValue(splitValue,
                (acc, v) -> getUsersAreaSplitByStep(acc, v, this::splitMaxAreaFieldsByStep),
                (acc) -> getSplitByLogin(acc, this::getMaxArea)
        );
    }

    private Map<String, Double> splitMaxAreaFieldsByStep(List<FieldResponse> responses, Double splitValue) {
        Map<String, Double> maxAreasSplitByStep = new HashMap<>();

        for (FieldResponse response : responses) {
            long bucket = (long) Math.floor(response.getArea() / splitValue);
            maxAreasSplitByStep.compute(
                    Long.toString(bucket), (k, v) -> v == null ? response.getArea() : Math.max(v, response.getArea())
            );
        }

        return maxAreasSplitByStep;
    }

    private Double getMaxArea(List<FieldResponse> responses) {
        return responses.stream()
                .mapToDouble(FieldResponse::getArea)
                .max().orElse(0.0);
    }

    public Map<String, Double> getUserAverageArea(Double splitValue) {
        return doActionBasedOnSplitValue(splitValue,
                (acc, v) -> getUsersAreaSplitByStep(acc, v, this::splitAverageAreaFieldsByStep),
                (acc) -> getSplitByLogin(acc, this::getAverageArea)
        );
    }

    private Map<String, Double> splitAverageAreaFieldsByStep(List<FieldResponse> responses, Double splitValue) {
        Map<String, Pair> averageAreasSplitByStep = new HashMap<>();

        for (FieldResponse response : responses) {
            long bucket = (long) Math.floor(response.getArea() / splitValue);
            averageAreasSplitByStep.compute(
                    String.format(splitString, bucket, bucket + 1),
                    (k, v) -> v == null ? new Pair() : v.increment(response.getArea())
            );
        }

        return averageAreasSplitByStep.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, v -> v.getValue().getAverage()));
    }

    private static class Pair {
        private double total = 0;
        private long counter = 0;

        public Pair increment(double value) {
            total += value;
            counter++;
            return this;
        }

        public double getAverage() {
            return total / counter;
        }
    }

    private Double getAverageArea(List<FieldResponse> responses) {
        return responses.stream()
                .mapToDouble(FieldResponse::getArea)
                .average().orElse(0.0);
    }

    public Map<String, Double> getUsersMinArea(Double splitValue) {
        return doActionBasedOnSplitValue(splitValue,
                (acc, v) -> getUsersAreaSplitByStep(acc, v, this::splitMinAreaFieldsByStep),
                (acc) -> getSplitByLogin(acc, this::getMinArea)
        );
    }

    private Map<String, Double> splitMinAreaFieldsByStep(List<FieldResponse> responses, Double splitValue) {
        Map<String, Double> minAreasSplitByStep = new HashMap<>();

        for (FieldResponse response : responses) {
            long bucket = (long) Math.floor(response.getArea() / splitValue);
            minAreasSplitByStep.compute(
                    Long.toString(bucket), (k, v) -> v == null ? response.getArea() : Math.min(v, response.getArea())
            );
        }

        return minAreasSplitByStep;
    }

    private Double getMinArea(List<FieldResponse> responses) {
        return responses.stream()
                .mapToDouble(FieldResponse::getArea)
                .min().orElse(0.0);
    }

    private Map<String, Double> doActionBasedOnSplitValue(Double splitValue,
                                                          BiFunction<List<Account>, Double, Map<String, Double>> positiveFunction,
                                                          Function<List<Account>, Map<String, Double>> zeroFunction) {
        List<Account> accounts = accountService.findAllByType(AccountType.rancher);

        if (Double.compare(splitValue, 0) > 1) {
            return positiveFunction.apply(accounts, splitValue);
        } else if (Double.compare(splitValue, 0) == 0) {
            return zeroFunction.apply(accounts);
        }

        throw new IllegalArgumentException("Split value can't be negative");
    }

    private Map<String, Double> getUsersAreaSplitByStep(List<Account> accounts, Double splitValue,
                                                       BiFunction<List<FieldResponse>, Double, Map<String, Double>> splitFunction) {
        List<FieldResponse> fieldResponses = accounts.stream()
                .map(this::mapAccountToFielderRequest)
                .map(fielderGrpcService::getByEmailAndTelephone)
                .map(FielderResponse::getFieldsList)
                .flatMap(Collection::stream).toList();

        return splitFunction.apply(fieldResponses, splitValue);
    }

    private Map<String, Double> getSplitByLogin(List<Account> accounts,
                                                Function<List<FieldResponse>, Double> splitFunction) {
        Map<String, Double> userMaxArea = new HashMap<>();
        for (Account account : accounts) {
            FielderRequest request = mapAccountToFielderRequest(account);
            FielderResponse response = fielderGrpcService.getByEmailAndTelephone(request);
            userMaxArea.put(account.getLogin(), splitFunction.apply(response.getFieldsList()));
        }
        return userMaxArea;
    }

    private FielderRequest mapAccountToFielderRequest(Account account) {
        return FielderRequest.newBuilder()
                .setEmail(account.getEmail())
                .setTelephone(StringValue.of(account.getTelephone()))
                .build();
    }
}
