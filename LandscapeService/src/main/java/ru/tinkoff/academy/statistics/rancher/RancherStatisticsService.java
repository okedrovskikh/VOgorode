package ru.tinkoff.academy.statistics.rancher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.account.Account;
import ru.tinkoff.academy.account.AccountService;
import ru.tinkoff.academy.account.type.AccountType;
import ru.tinkoff.academy.proto.rancher.fielder.FieldResponse;
import ru.tinkoff.academy.proto.rancher.fielder.FielderRequest;
import ru.tinkoff.academy.proto.rancher.fielder.FielderResponse;
import ru.tinkoff.academy.rancher.FielderGrpcService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Map<String, Double> getUsersMaxArea(Double spliter) {
        Map<String, Double> userMaxArea = new HashMap<>();
        List<Account> accounts = accountService.findAllByType(AccountType.rancher);
        for (Account account : accounts) {
            FielderRequest request = mapAccountToFielderRequest(account);
            FielderResponse response = fielderGrpcService.getByEmailOrTelephone(request);
            userMaxArea.put(account.getLogin(), getMaxArea(response.getFieldsList()));
        }
        return userMaxArea;
    }

    private FielderRequest mapAccountToFielderRequest(Account account) {
        return FielderRequest.newBuilder()
                .setEmail(account.getEmail())
                .setTelephone(account.getTelephone())
                .build();
    }

    private Double getMaxArea(List<FieldResponse> responses) {
        return responses.stream()
                .max(fieldComparator)
                .orElse(NULL_FIELD_AREA).getArea().getX();
    }

    public Map<String, Double> getUsersMinArea(Double spliter) {
        Map<String, Double> userMinArea = new HashMap<>();
        List<Account> accounts = accountService.findAllByType(AccountType.rancher);
        for (Account account : accounts) {
            FielderRequest request = mapAccountToFielderRequest(account);
            FielderResponse response = fielderGrpcService.getByEmailOrTelephone(request);
            userMinArea.put(account.getLogin(), getMinArea(response.getFieldsList()));
        }
        return userMinArea;
    }

    private Double getMinArea(List<FieldResponse> responses) {
        return responses.stream()
                .min(fieldComparator)
                .orElse(NULL_FIELD_AREA).getArea().getX();
    }

    public Map<String, Double> getUserAverageArea(Double spliter) {
        Map<String, Double> userAverageArea = new HashMap<>();
        List<Account> accounts = accountService.findAllByType(AccountType.rancher);
        for (Account account : accounts) {
            FielderRequest request = mapAccountToFielderRequest(account);
            FielderResponse response = fielderGrpcService.getByEmailOrTelephone(request);
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
