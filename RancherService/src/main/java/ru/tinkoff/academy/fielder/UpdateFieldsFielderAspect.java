package ru.tinkoff.academy.fielder;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.field.Field;
import ru.tinkoff.academy.field.FieldService;

@Aspect
@Component
@RequiredArgsConstructor
public class UpdateFieldsFielderAspect {
    private final FieldService fieldService;

    @AfterReturning(pointcut = "execution(* ru.tinkoff.academy.fielder.FielderMapper.updateFielder(Fielder, ru.tinkoff.academy.fielder.dto.FielderUpdateDto)) || " +
            "execution(* ru.tinkoff.academy.fielder.FielderMapper.dtoToFielder(ru.tinkoff.academy.fielder.dto.FielderCreateDto))",
            returning = "fielder")
    public void setFieldsFielder(Fielder fielder) {
        fieldService.updateFieldsFielder(fielder.getFields().stream().map(Field::getId).toList(), fielder);
    }
}
