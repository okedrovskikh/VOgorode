package ru.tinkoff.academy.system.status;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SystemStatusService {
    @Getter
    private SystemStatus systemStatus = SystemStatus.OK;


    private final Logger logger = LoggerFactory.getLogger(SystemStatusService.class);

    /**
     * Change system status to Malfunction defined on value of {@code isChangeTo}
     *
     * @param isChangeTo if {@code true} change status to {@link SystemStatus#MALFUNCTION},
     *                   if {@code false} change status to {@link SystemStatus#OK}
     */
    public synchronized void changeToMalfunction(boolean isChangeTo) {
        SystemStatus oldSystemStatus = systemStatus;
        systemStatus = isChangeTo ? SystemStatus.MALFUNCTION : SystemStatus.OK;
        logger.info(createChangeSystemStatusLogString(oldSystemStatus, systemStatus));
    }

    private String createChangeSystemStatusLogString(SystemStatus oldStatus, SystemStatus newStatus) {
        return String.format("Change from %s to %s", oldStatus, newStatus);
    }
}
