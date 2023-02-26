package ru.tinkoff.academy.system;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.system.status.SystemStatus;
import ru.tinkoff.academy.system.status.SystemStatusService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SystemService {
    private final BuildProperties buildProperties;
    private final SystemStatusService systemStatusService;

    /**
     * Get readiness state of service
     *
     * @return {@link Map} with service name as key and {@link SystemStatus} readiness state as value
     */
    public Map<String, SystemStatus> getReadiness() {
        return Map.of(buildProperties.getName(), systemStatusService.getSystemStatus());
    }

    /**
     * Change service status to Malfunction defined on value of {@code isChangeTo}
     *
     * @param isChangeTo if {@code true} change status to {@link SystemStatus#MALFUNCTION},
     *                   if {@code false} change status to {@link SystemStatus#OK}
     */
    public void forceMalfunction(boolean isChangeTo) {
        systemStatusService.changeToMalfunction(isChangeTo);
    }
}
