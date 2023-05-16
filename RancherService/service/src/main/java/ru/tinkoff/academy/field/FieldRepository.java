package ru.tinkoff.academy.field;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends MongoRepository<Field, String> {
    @Query(value = "select distinct on(floor(ST_Area(area) / :splitValue)) " +
            "floor(st_area(area) / :splitValue), " +
            "max(st_area(area)) over (partition by floor(ST_Area(area) / :splitValue)), " +
            "avg(st_area(area)) over (partition by floor(ST_Area(area) / :splitValue)), " +
            "min(st_area(area)) over (partition by floor(ST_Area(area) / :splitValue)) " +
            "from field")
    List<Object> findAreasStatBySplitValue(Double splitValue);

    @Query(value = "select distinct on(fielder_id) email, telephone, " +
            "max(st_area(area)) over (partition by fielder_id), " +
            "avg(st_area(area)) over (partition by fielder_id), " +
            "min(st_area(area)) over (partition by fielder_id) " +
            "from field join fielder on fielder.id = fielder_id " +
            "group by fielder_id, area, email, telephone")
    List<Object> findAreasStat();
}
