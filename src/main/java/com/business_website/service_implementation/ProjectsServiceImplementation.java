package com.business_website.service_implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.business_website.dto.ProjectsDto;
import com.business_website.models.Projects;
import com.business_website.repository.ProjectsRepo;
import com.business_website.services.ProjectService;

@Service
public class ProjectsServiceImplementation implements ProjectService{

    @Autowired
    private ProjectsRepo projectsRepo;

    @Autowired 
    private FileUploadServiceImplementation fileUploadServiceImplementation;

    @Override
    public Projects save(ProjectsDto projectsDto, MultipartFile file) {
        if(!file.isEmpty()){
            String imageUrl = fileUploadServiceImplementation.saveFile(file);
            projectsDto.setImageUrl(imageUrl);
        }
        Projects project = new Projects(projectsDto.getTitle(), projectsDto.getContent(), projectsDto.getImageUrl() ,projectsDto.getCreatedAt());
        return projectsRepo.save(project);
        }

    @Override
    public List<Projects> getAllProjects() {
        return projectsRepo.findAllByOrderByCreatedDateDesc();
    }
    @Override
    public Projects getProjectById(Long id) {
        Projects project = projectsRepo.findById(id).orElseThrow();
        return project;
    }

    @Override
    public void deleteProject(Long id) {
       Projects project = projectsRepo.findById(id).get();
       projectsRepo.delete(project);
    }

    @Override
    public Projects updateProject(ProjectsDto project, Long id, MultipartFile file) {
            Projects existingpProject = projectsRepo.findById(id).get();
            existingpProject.setTitle(project.getTitle());   
            existingpProject.setContent(project.getContent());   
        
        if(!file.isEmpty()){
            String imageUrl = fileUploadServiceImplementation.saveFile(file);
            existingpProject.setImageUrl(imageUrl);
        }
        
        return projectsRepo.save(existingpProject);
    }
   

}
