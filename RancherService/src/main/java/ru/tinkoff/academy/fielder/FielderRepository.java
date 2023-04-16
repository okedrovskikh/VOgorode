package ru.tinkoff.academy.fielder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FielderRepository extends JpaRepository<Fielder, Long> {
}
