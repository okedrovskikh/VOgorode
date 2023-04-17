package ru.tinkoff.academy.field;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.fielder.FielderService;

@Component
@RequiredArgsConstructor
public class FieldMapperSetup {
    private final FielderService fielderService;
    private final FieldMapper fieldMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void setupFieldMapper() {
        fieldMapper.setFielderService(fielderService);
    }
}
