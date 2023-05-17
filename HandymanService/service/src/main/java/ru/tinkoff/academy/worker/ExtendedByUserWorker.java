package ru.tinkoff.academy.worker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.academy.landscape.account.Account;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtendedByUserWorker {
    private String id;
    private List<WorkEnum> services;
    private Double latitude;
    private Double longitude;

    private Account account;
}
