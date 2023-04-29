package ru.tinkoff.academy.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query(nativeQuery = true, value = "select min(creation_date) from account;")
    Timestamp findEarliestCreationDate();

    @Query(nativeQuery = true, value = "select max(creation_date) from account;")
    Timestamp findLatestCreationDate();

    Optional<Account> findByEmailAndTelephone(String email, String telephone);
}
