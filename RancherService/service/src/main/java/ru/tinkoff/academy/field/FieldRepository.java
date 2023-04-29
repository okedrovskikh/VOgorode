package ru.tinkoff.academy.field;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    @Query(nativeQuery = true, value = "select distinct on(floor(ST_Area(area) / :splitValue)) " +
            "floor(st_area(area) / :splitValue), " +
            "max(st_area(area)), " +
            "avg(st_area(area)), " +
            "min(st_area(area)) " +
            "from field group by floor(ST_Area(area) / :splitValue), area;")
    List<Object> findAreasStatBySplitValue(Double splitValue);

    @Query(nativeQuery = true, value = "select distinct on(fielder_id) email, telephone, " +
            "max(st_area(area)) over (partition by fielder_id), " +
            "avg(st_area(area)) over (partition by fielder_id), " +
            "min(st_area(area)) over (partition by fielder_id) " +
            "from field join fielder on fielder.id = fielder_id " +
            "group by fielder_id, area, email, telephone;")
    List<Object> findAreasStat();
}
