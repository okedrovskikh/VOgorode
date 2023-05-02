package ru.tinkoff.academy.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.proto.gardener.GardenerResponse;
import ru.tinkoff.academy.rancher.garden.GardenGrpcClient;
import ru.tinkoff.academy.rancher.gardener.GardenerGrpcClient;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final GardenGrpcClient gardenGrpcClient;
    private final GardenerGrpcClient gardenerGrpcClient;

    public Report formReport() {
        Iterator<GardenerResponse> gardenerResponseIter = gardenerGrpcClient.findAllGardeners();

        return null;
    }
}
