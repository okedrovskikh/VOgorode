package ru.tinkoff.academy.area;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.field.Field;
import ru.tinkoff.academy.field.FieldMapper;
import ru.tinkoff.academy.field.FieldService;
import ru.tinkoff.academy.proto.area.AreaServiceGrpc;
import ru.tinkoff.academy.proto.area.AreaStat;
import ru.tinkoff.academy.proto.area.AreaStatRequest;
import ru.tinkoff.academy.proto.area.AreaStatResponse;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@GrpcService
@RequiredArgsConstructor
public class AreaServiceGrpcImpl extends AreaServiceGrpc.AreaServiceImplBase {
    private static final String splitRange = "%s - %s";
    private static final String splitByEmailAndTelephone = "%s:%s";
    private static final int MAX_AREA_STATS_PER_RESPONSE = 10000;

    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    @Override
    public void getAreasStatSplitByEmailAndTelephone(Empty request, StreamObserver<AreaStatResponse> responseObserver) {
        List<AreaStat> stats = computeStats(computeStatsSplitByEmailAndTelephone(fieldService.findAll()));

        sendResponse(stats, responseObserver);
        responseObserver.onCompleted();
    }

    private Map<String, List<Double>> computeStatsSplitByEmailAndTelephone(List<Field> fields) {
        Map<String, List<Double>> res = new HashMap<>();

        for (Field field : fields) {
            double areaSquare = fieldMapper.getAreaSquare(field);
            String key = formEmailAndTelephoneSplitValue(field.getGardener().getEmail(), field.getGardener().getTelephone());
            res.compute(key, (k, v) -> v == null ? new ArrayList<>(List.of(areaSquare)) : add(v, areaSquare));
        }

        return res;
    }

    private String formEmailAndTelephoneSplitValue(String email, String telephone) {
        return String.format(splitByEmailAndTelephone, email, telephone);
    }

    @Override
    public void getAreasStatBySplitValue(AreaStatRequest request, StreamObserver<AreaStatResponse> responseObserver) {
        List<AreaStat> stats = computeStats(computeStatBySplitValue(fieldService.findAll(), request.getSplitValue()));

        sendResponse(stats, responseObserver);
        responseObserver.onCompleted();
    }

    private Map<String, List<Double>> computeStatBySplitValue(List<Field> fields, Double splitValue) {
        Map<String, List<Double>> res = new HashMap<>();

        for (Field field : fields) {
            double areaSquare = fieldMapper.getAreaSquare(field);
            String key = formSplitRange((long) (areaSquare / splitValue), splitValue.longValue());
            res.compute(key, (k, v) -> v == null ? new ArrayList<>(List.of(areaSquare)) : add(v, areaSquare));
        }

        return res;
    }

    private String formSplitRange(long splitBucket, long multilayer) {
        return String.format(splitRange, splitBucket * multilayer, (splitBucket + 1) * multilayer);
    }

    private void sendResponse(List<AreaStat> stats, StreamObserver<AreaStatResponse> responseObserver) {
        AreaStatResponse.Builder nextResponse = AreaStatResponse.newBuilder();
        int counter = 0;
        for (AreaStat stat : stats) {

            if (counter > MAX_AREA_STATS_PER_RESPONSE) {
                responseObserver.onNext(nextResponse.build());
                nextResponse.clear();
                counter = 0;
            }

            nextResponse.addStats(stat);
            counter++;
        }

        responseObserver.onNext(nextResponse.build());
    }

    private List<AreaStat> computeStats(Map<String, List<Double>> statMap) {
        List<AreaStat> areaStats = new ArrayList<>(statMap.keySet().size());

        for (Entry<String, List<Double>> statMapEntry : statMap.entrySet()) {
            DoubleSummaryStatistics summaryStatistics = statMapEntry.getValue().stream()
                    .mapToDouble(v -> v)
                    .summaryStatistics();

            areaStats.add(AreaStat.newBuilder()
                    .setSplitValue(statMapEntry.getKey())
                    .setMax(summaryStatistics.getMax())
                    .setAverage(summaryStatistics.getAverage())
                    .setMin(summaryStatistics.getMin())
                    .build());
        }

        return areaStats;
    }

    private List<Double> add(List<Double> list, double element) {
        list.add(element);
        return list;
    }
}
