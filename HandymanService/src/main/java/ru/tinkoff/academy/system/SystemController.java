package ru.tinkoff.academy.system;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {
    private final BuildProperties buildProperties;

    /**
     * Check service liveness
     */
    @GetMapping("/liveness")
    public void getLiveness() {

    }

    /**
     * Check service readiness
     *
     * @return {@link Map} with service name as key and readiness status as value
     */
    @GetMapping("/readiness")
    public Map<String, String> getReadiness() {
        return Map.of(buildProperties.getName(), "OK");
    }
}
