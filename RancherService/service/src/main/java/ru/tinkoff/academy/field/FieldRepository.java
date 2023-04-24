package ru.tinkoff.academy.field;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    @Query(nativeQuery = true, value = "select floor(ST_Area(area) / :splitValue), " +
            "max(ST_Area(area)), avg(ST_Area(area)), min(ST_Area(area)) " +
            "from field group by floor(ST_Area(area) / :splitValue);")
    List<Object> findAreasStatBySplitValue(Double splitValue);
}
