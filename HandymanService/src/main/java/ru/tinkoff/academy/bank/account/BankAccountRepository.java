package ru.tinkoff.academy.bank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Query(nativeQuery = true, value = "select bank from bank_account where bank is not null group by bank")
    List<String> findAllBanks();
}
