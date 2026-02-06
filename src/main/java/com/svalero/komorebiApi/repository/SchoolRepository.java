package com.svalero.komorebiApi.repository;

import com.svalero.komorebiApi.domain.School;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends CrudRepository<School, Long> {
    List<School> findAll();

    @Query("SELECT a FROM School a WHERE " +
            "(:name IS NULL OR a.name LIKE %:name%) AND " +
            "(:city IS NULL OR a.city LIKE %:city%)")
    List<School> findByFilters(
            @Param("name") String name,
            @Param("city") String city

    );
}
