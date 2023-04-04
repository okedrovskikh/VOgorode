package ru.tinkoff.academy.system;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.system.status.SystemStatus;

import java.util.Map;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {
    private final SystemService systemService;

    /**
     * Check service liveness
     */
    @GetMapping("/liveness")
    public void getLiveness() {

    }

    /**
     * Check service readiness
     *
     * @return {@link Map} with service name as key and {@link SystemStatus} as value
     */
    @GetMapping("/readiness")
    public Map<String, SystemStatus> getReadiness() {
        return systemService.getReadiness();
    }

    /**
     * Change service status to Malfunction defined on value of {@code isChangeTo}
     *
     * @param isChangeTo if {@code true} change status to {@link SystemStatus#MALFUNCTION},
     *                   if {@code false} change status to {@link SystemStatus#OK}
     */
    @GetMapping("/forceMalfunction")
    public void forceMalfunction(@RequestParam(name = "isChangeTo", required = false, defaultValue = "true") boolean isChangeTo) {
        systemService.forceMalfunction(isChangeTo);
    }
}
