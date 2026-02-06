package com.svalero.komorebiApi.repository;

import com.svalero.komorebiApi.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    List<Project> findAll();
    List<Project> findByName(String name);
    List<Project> findByDescription(String description);
    List<Project> findByNameAndDescription(String name, String description);

}
