package ru.tinkoff.academy.fielder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FielderRepository extends JpaRepository<Fielder, Long> {
    Optional<Fielder> findByEmailOrTelephone(String email, String telephone);
}
