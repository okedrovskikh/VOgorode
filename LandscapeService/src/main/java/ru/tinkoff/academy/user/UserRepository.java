package ru.tinkoff.academy.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Modifying
    @Query("update ru.tinkoff.academy.user.User u set u = :user where u = :user")
    int update(@Param("user") User user);
}
