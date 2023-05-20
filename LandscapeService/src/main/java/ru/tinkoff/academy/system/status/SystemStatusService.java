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

    public synchronized void switchToMalfunction() {
        SystemStatus oldSystemStatus = systemStatus;
        systemStatus = SystemStatus.MALFUNCTION;
        logger.info(createChangeSystemStatusLogString(oldSystemStatus, systemStatus));
    }

    public synchronized void switchToOK() {
        SystemStatus oldSystemStatus = systemStatus;
        systemStatus = SystemStatus.OK;
        logger.info(createChangeSystemStatusLogString(oldSystemStatus, systemStatus));
    }

    private String createChangeSystemStatusLogString(SystemStatus oldStatus, SystemStatus newStatus) {
        return String.format("Change from %s to %s", oldStatus, newStatus);
    }
}
