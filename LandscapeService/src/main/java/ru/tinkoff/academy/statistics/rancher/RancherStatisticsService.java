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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RancherStatisticsService {
    private static final FieldResponse NULL_FIELD_AREA = FieldResponse.newBuilder().
            setArea(FieldResponse.Point.newBuilder()
                    .setX(0.0)
                    .setY(0.0)
                    .build()
            ).build();

    private final AccountService accountService;
    private final FielderGrpcService fielderGrpcService;
    private final FieldResponseComparator fieldComparator;

    public Map<String, Double> getUsersMaxArea(Double splitValue) {
        List<Account> accounts = accountService.findAllByType(AccountType.rancher);

        if (Double.compare(splitValue, 0) > 1) {
            return getUsersMaxSplitByStep(accounts, splitValue);
        } else if (Double.compare(splitValue, 0) == 0) {
            return getUserMaxAreaSplitByLogin(accounts);
        }

        throw new IllegalArgumentException("Split value can't be negative");
    }

    private Map<String, Double> getUsersMaxSplitByStep(List<Account> accounts, Double splitValue) {
        List<FieldResponse> fieldResponses = accounts.stream()
                .map(this::mapAccountToFielderRequest)
                .map(fielderGrpcService::getByEmailAndTelephone)
                .map(FielderResponse::getFieldsList)
                .flatMap(Collection::stream).toList();

        return splitMaxAreaFieldsByStep(fieldResponses, splitValue);
    }

    private Map<String, Double> splitMaxAreaFieldsByStep(List<FieldResponse> responses, Double splitValue) {
        Map<String, Double> maxAreasSplitByStep = new HashMap<>();

        for (FieldResponse response : responses) {
            long bucket = (long) Math.floor(response.getArea().getX() / splitValue);
            maxAreasSplitByStep.compute(
                    Long.toString(bucket), (k, v) -> v == null ? response.getArea().getX() : Math.max(v, response.getArea().getX())
            );
        }

        return maxAreasSplitByStep;
    }

    private Map<String, Double> getUserMaxAreaSplitByLogin(List<Account> accounts) {
        Map<String, Double> userMaxArea = new HashMap<>();
        for (Account account : accounts) {
            FielderRequest request = mapAccountToFielderRequest(account);
            FielderResponse response = fielderGrpcService.getByEmailAndTelephone(request);
            userMaxArea.put(account.getLogin(), getMaxArea(response.getFieldsList()));
        }
        return userMaxArea;
    }

    private FielderRequest mapAccountToFielderRequest(Account account) {
        return FielderRequest.newBuilder()
                .setEmail(account.getEmail())
                .setTelephone(StringValue.of(account.getTelephone()))
                .build();
    }

    private Double getMaxArea(List<FieldResponse> responses) {
        return responses.stream()
                .max(fieldComparator)
                .orElse(NULL_FIELD_AREA).getArea().getX();
    }

    public Map<String, Double> getUsersMinArea(Double splitValue) {
        List<Account> accounts = accountService.findAllByType(AccountType.rancher);

        if (Double.compare(splitValue, 0) > 1) {
            return getUsersMinSplitByStep(accounts, splitValue);
        } else if (Double.compare(splitValue, 0) == 0) {
            return getUserMinAreaSplitByLogin(accounts);
        }

        throw new IllegalArgumentException("Split value can't be negative");
    }

    private Map<String, Double> getUsersMinSplitByStep(List<Account> accounts, Double splitValue) {
        List<FieldResponse> fieldResponses = accounts.stream()
                .map(this::mapAccountToFielderRequest)
                .map(fielderGrpcService::getByEmailAndTelephone)
                .map(FielderResponse::getFieldsList)
                .flatMap(Collection::stream).toList();

        return splitMinAreaFieldsByStep(fieldResponses, splitValue);
    }

    private Map<String, Double> splitMinAreaFieldsByStep(List<FieldResponse> responses, Double splitValue) {
        Map<String, Double> minAreasSplitByStep = new HashMap<>();

        for (FieldResponse response : responses) {
            long bucket = (long) Math.floor(response.getArea().getX() / splitValue);
            minAreasSplitByStep.compute(
                    Long.toString(bucket), (k, v) -> v == null ? response.getArea().getX() : Math.min(v, response.getArea().getX())
            );
        }

        return minAreasSplitByStep;
    }

    private Map<String, Double> getUserMinAreaSplitByLogin(List<Account> accounts) {
        Map<String, Double> userMinArea = new HashMap<>();
        for (Account account : accounts) {
            FielderRequest request = mapAccountToFielderRequest(account);
            FielderResponse response = fielderGrpcService.getByEmailAndTelephone(request);
            userMinArea.put(account.getLogin(), getMinArea(response.getFieldsList()));
        }
        return userMinArea;
    }

    private Double getMinArea(List<FieldResponse> responses) {
        return responses.stream()
                .min(fieldComparator)
                .orElse(NULL_FIELD_AREA).getArea().getX();
    }

    public Map<String, Double> getUserAverageArea(Double splitValue) {
        List<Account> accounts = accountService.findAllByType(AccountType.rancher);

        if (Double.compare(splitValue, 0) > 1) {
            return getUsersAverageSplitByStep(accounts, splitValue);
        } else if (Double.compare(splitValue, 0) == 0) {
            return getUserAverageAreaSplitByLogin(accounts);
        }

        throw new IllegalArgumentException("Split value can't be negative");
    }

    private Map<String, Double> getUsersAverageSplitByStep(List<Account> accounts, Double splitValue) {
        List<FieldResponse> fieldResponses = accounts.stream()
                .map(this::mapAccountToFielderRequest)
                .map(fielderGrpcService::getByEmailAndTelephone)
                .map(FielderResponse::getFieldsList)
                .flatMap(Collection::stream).toList();

        return splitAverageAreaFieldsByStep(fieldResponses, splitValue);
    }

    private Map<String, Double> splitAverageAreaFieldsByStep(List<FieldResponse> responses, Double splitValue) {
        Map<String, Pair> averageAreasSplitByStep = new HashMap<>();

        for (FieldResponse response : responses) {
            long bucket = (long) Math.floor(response.getArea().getX() / splitValue);
            averageAreasSplitByStep.compute(
                    Long.toString(bucket), (k, v) -> v == null ? new Pair() : v.increment(response.getArea().getX())
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

    private Map<String, Double> getUserAverageAreaSplitByLogin(List<Account> accounts) {
        Map<String, Double> userAverageArea = new HashMap<>();
        for (Account account : accounts) {
            FielderRequest request = mapAccountToFielderRequest(account);
            FielderResponse response = fielderGrpcService.getByEmailAndTelephone(request);
            userAverageArea.put(account.getLogin(), getAverageArea(response.getFieldsList()));
        }
        return userAverageArea;
    }

    private Double getAverageArea(List<FieldResponse> responses) {
        return responses.stream()
                .map(FieldResponse::getArea)
                .mapToDouble(FieldResponse.Point::getX)
                .average().orElse(0.0);
    }

    @RequiredArgsConstructor
    public static final class FieldResponseComparator implements Comparator<FieldResponse> {
        private final PointComparator pointComparator;

        @Override
        public int compare(FieldResponse o1, FieldResponse o2) {
            if (o1 == o2) {
                return 0;
            }

            if (o1 == null) {
                return -1;
            }

            if (o2 == null) {
                return 1;
            }

            return pointComparator.compare(o1.getArea(), o2.getArea());
        }
    }

    public static final class PointComparator implements Comparator<FieldResponse.Point> {

        @Override
        public int compare(FieldResponse.Point o1, FieldResponse.Point o2) {
            if (o1.getX() != o2.getX()) {
                return o1.getX() > o2.getX() ? 1 : -1;
            }

            if (o1.getY() != o2.getY()) {
                return o1.getY() > o2.getY() ? 1 : -1;
            }

            return 0;
        }
    }
}
