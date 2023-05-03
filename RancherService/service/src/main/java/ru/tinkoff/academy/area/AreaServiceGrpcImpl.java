package ru.tinkoff.academy.area;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.field.FieldService;
import ru.tinkoff.academy.proto.area.AreaServiceGrpc;
import ru.tinkoff.academy.proto.area.AreaStat;
import ru.tinkoff.academy.proto.area.AreaStatRequest;
import ru.tinkoff.academy.proto.area.AreaStatResponse;

import java.util.List;
import java.util.function.Function;

@GrpcService
@RequiredArgsConstructor
public class AreaServiceGrpcImpl extends AreaServiceGrpc.AreaServiceImplBase {
    private static final String splitRange = "%s - %s";
    private static final String splitByEmailAndTelephone = "%s:%s";
    private static final int MAX_AREA_STATS_PER_RESPONSE = 10000;

    private final FieldService fieldService;

    @Override
    public void getAreasStatSplitByEmailAndTelephone(Empty request, StreamObserver<AreaStatResponse> responseObserver) {
        List<Object[]> stats = fieldService.findAreasStat();

        sendResponse(stats, this::mapToAreaStatSplitByEmailAndTelephone, responseObserver);
        responseObserver.onCompleted();
    }

    private AreaStat mapToAreaStatSplitByEmailAndTelephone(Object[] stat) {
        String splitByEmailAndTelephone = formEmailAndTelephoneSplitValue((String) stat[0], (String) stat[1]);
        return AreaStat.newBuilder()
                .setSplitValue(splitByEmailAndTelephone)
                .setMax((Double) stat[2])
                .setAverage((Double) stat[3])
                .setMin((Double) stat[4])
                .build();
    }

    private String formEmailAndTelephoneSplitValue(String email, String telephone) {
        return String.format(splitByEmailAndTelephone, email, telephone);
    }

    @Override
    public void getAreasStatBySplitValue(AreaStatRequest request, StreamObserver<AreaStatResponse> responseObserver) {
        try {
            List<Object[]> stats = fieldService.findAreasStatBySplitValue(request.getSplitValue());

            sendResponse(stats, (stat) -> mapToAreaStatBySplitRange(stat, (long) request.getSplitValue()), responseObserver);
            responseObserver.onCompleted();
        } catch (Throwable t) {
            responseObserver.onError(t);
        }
    }

    private AreaStat mapToAreaStatBySplitRange(Object[] stats, long splitValue) {
        String splitRange = formSplitRange(((Double) stats[0]).longValue(), splitValue);
        return AreaStat.newBuilder()
                .setSplitValue(splitRange)
                .setMax((Double) stats[1])
                .setAverage((Double) stats[2])
                .setMin((Double) stats[3])
                .build();
    }

    private String formSplitRange(long splitBucket, long multilayer) {
        return String.format(splitRange, splitBucket * multilayer, (splitBucket + 1) * multilayer);
    }

    private void sendResponse(List<Object[]> stats, Function<Object[], AreaStat> mappingFunction, StreamObserver<AreaStatResponse> responseObserver) {
        AreaStatResponse.Builder nextResponse = AreaStatResponse.newBuilder();
        int counter = 0;
        for (Object[] stat : stats) {
            AreaStat areaStat = mappingFunction.apply(stat);

            if (counter > MAX_AREA_STATS_PER_RESPONSE) {
                responseObserver.onNext(nextResponse.build());
                nextResponse.clear();
                counter = 0;
            }

            nextResponse.addStats(areaStat);
            counter++;
        }

        responseObserver.onNext(nextResponse.build());
    }
}
