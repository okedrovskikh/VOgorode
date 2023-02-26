package ru.tinkoff.academy.system.status;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class SystemStatusService {
    @Getter
    private SystemStatus systemStatus = SystemStatus.OK;

    /**
     * Change system status to Malfunction defined on value of {@code isChangeTo}
     *
     * @param isChangeTo if {@code true} change status to {@link SystemStatus#MALFUNCTION},
     *                   if {@code false} change status to {@link SystemStatus#OK}
     */
    public synchronized void changeToMalfunction(boolean isChangeTo) {
        systemStatus = isChangeTo ? SystemStatus.MALFUNCTION : SystemStatus.OK;
    }
}
