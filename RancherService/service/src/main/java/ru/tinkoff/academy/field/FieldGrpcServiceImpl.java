package ru.tinkoff.academy.field;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.proto.field.AreaStat;
import ru.tinkoff.academy.proto.field.AreaStatRequest;
import ru.tinkoff.academy.proto.field.AreaStatResponse;
import ru.tinkoff.academy.proto.field.FieldServiceGrpc;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class FieldGrpcServiceImpl extends FieldServiceGrpc.FieldServiceImplBase {
    private static final String splitRange = "%s - %s";
    private static final String splitByEmailAndTelephone = "%s:%s";

    private final FieldService fieldService;

    @Override
    public void getAreasStatSplitByEmailAndTelephone(Empty request, StreamObserver<AreaStatResponse> responseObserver) {
        try {
            List<AreaStat> stats = fieldService.findAreasStat().stream()
                    .map(this::mapToAreaStatSplitByEmailAndTelephone)
                    .toList();

            responseObserver.onNext(buildResponse(stats));
            responseObserver.onCompleted();
        } catch (Throwable t) {
            responseObserver.onError(t);
        }
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
            List<AreaStat> stats = fieldService.findAreasStatBySplitValue(request.getSplitValue()).stream()
                    .map((stat) -> mapToAreaStatBySplitRange(stat, (long) request.getSplitValue()))
                    .toList();

            responseObserver.onNext(buildResponse(stats));
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

    private AreaStatResponse buildResponse(List<AreaStat> stats) {
        return AreaStatResponse.newBuilder()
                .addAllResponse(stats)
                .build();
    }
}
