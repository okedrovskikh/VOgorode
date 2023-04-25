package ru.tinkoff.academy.user;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    @EntityGraph(value = "User.bankAccounts")
    Optional<User> findById(Long id);

    @Override
    @EntityGraph(value = "User.bankAccounts")
    List<User> findAll();

    @Override
    @EntityGraph(value = "User.bankAccounts")
    List<User> findAll(Sort sort);
}
