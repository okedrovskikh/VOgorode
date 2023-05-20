package ru.tinkoff.academy.fielder;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface FielderRepository extends JpaRepository<Fielder, Long> {
    @Override
    @EntityGraph(value = "Fielder.fields")
    Optional<Fielder> findById(Long id);

    @Override
    @EntityGraph(value = "Fielder.fields")
    List<Fielder> findAll();
}
