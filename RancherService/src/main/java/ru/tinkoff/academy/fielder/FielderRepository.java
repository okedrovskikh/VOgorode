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

    @Query(nativeQuery = true, value = "select max(ST_Area(area)), avg(ST_Area(area)), min(ST_Area(area)) " +
            "filter ( where fielder_id is not null and fielder_id = (select fielder.id from fielder " +
            "where :email = email and telephone is null) ) " +
            "from field;")
    Optional<Double[]> findAreasStatByEmail(String email);

    @Query(nativeQuery = true, value = "select max(ST_Area(area)), avg(ST_Area(area)), min(ST_Area(area)) " +
            "filter ( where fielder_id is not null and fielder_id = (select fielder.id from fielder " +
            "where :email = email and :telephone = telephone) ) " +
            "from field;")
    Optional<Double[]> findAreasStatByEmailAndTelephone(String email, String telephone);
}
