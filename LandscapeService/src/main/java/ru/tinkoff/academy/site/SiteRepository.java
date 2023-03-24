package ru.tinkoff.academy.site;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    @Modifying
    @Query("update ru.tinkoff.academy.site.Site s set s = :site where s = :site")
    int update(@Param("site") Site site);
}
