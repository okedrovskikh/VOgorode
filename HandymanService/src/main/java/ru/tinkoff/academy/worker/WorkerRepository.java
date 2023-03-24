package ru.tinkoff.academy.worker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Modifying
    @Query("update ru.tinkoff.academy.worker.Worker w set w = :worker where w = :worker")
    int update(@Param("worker") Worker worker);
}
