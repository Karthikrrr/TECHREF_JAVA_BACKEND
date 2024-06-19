package com.business_website.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.business_website.dto.ProjectsDto;
import com.business_website.models.Projects;

public interface ProjectService {
    Projects save(ProjectsDto projectsDto, MultipartFile file);
    List<Projects> getAllProjects();
    void deleteProject(Long id);
    Projects getProjectById(Long id);
    Projects updateProject(ProjectsDto projectDto, Long id, MultipartFile file);
}
