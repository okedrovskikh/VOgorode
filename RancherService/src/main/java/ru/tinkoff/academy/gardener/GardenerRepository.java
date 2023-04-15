package ru.tinkoff.academy.gardener;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GardenerRepository extends JpaRepository<Gardener, Long> {
}
