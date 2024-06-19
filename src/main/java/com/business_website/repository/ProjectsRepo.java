package com.business_website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.business_website.dto.ProjectsDto;
import com.business_website.models.Projects;

@Repository
public interface ProjectsRepo extends JpaRepository<Projects, Long>{

    @Query(value = "SELECT * FROM projects ORDER BY created_at DESC", nativeQuery = true)
    List<Projects> findAllByOrderByCreatedDateDesc();
    
    Projects save(ProjectsDto projectsDto);

    @Override
    void delete(Projects projects);
}
