package com.business_website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.business_website.dto.ProjectsDto;
import com.business_website.models.Projects;
import com.business_website.service_implementation.ProjectsServiceImplementation;

@Controller
@ControllerAdvice
public class ProjectsController {

    
    @Autowired
    private  ProjectsServiceImplementation projectsServiceImplementation;


    @GetMapping("/projects")
    public String getAllProjects(Model model){
        List<Projects> projects = projectsServiceImplementation.getAllProjects();
        model.addAttribute("projects" , projects);
        return "projects-view";
    }



    //ADMIN Controller 
    @GetMapping("admin-page/projects")
    public String getAllProjectsForAdmin(Model model){
        List<Projects> projects = projectsServiceImplementation.getAllProjects();
        model.addAttribute("projects" , projects);
        return "admin-project-view";
    }
    

    @GetMapping("admin-page/projects/new")
    public String createProjectsForm(Model model){
        return "create-project";
        
    }

    @PostMapping("admin-page/projects/new")
    public String saveProject(@ModelAttribute("project") ProjectsDto projectDto, @RequestParam("image") MultipartFile file ,Model model){
        if(file.isEmpty()){
            model.addAttribute("message", "File is Empty!!!");
            return "create-project";
        }
        else if(file.getSize() >  1048576){
            model.addAttribute("message", "FileSize is Greater Than 1MB");
            return "create-project";
        }
        else{
            projectsServiceImplementation.save(projectDto, file);
            model.addAttribute("sucess", "Projects Added Sucessfull");
            return "redirect:/admin-page";
        }
        
    }
    @GetMapping("admin-page/projects/update/{id}")
    public String showUpdateProjectForm(@PathVariable("id") Long id, Model model) {
        Projects project = projectsServiceImplementation.getProjectById(id);
        model.addAttribute("project", project);
        return "admin-project-update";
    }

    @PostMapping("admin-page/projects/update/{id}")
    public String updateProject(
            @PathVariable("id") Long id,
            @ModelAttribute("project") ProjectsDto projectDto,
            @RequestParam("image") MultipartFile file)
             {
        projectsServiceImplementation.updateProject(projectDto, id, file);
        
        return "redirect:/admin-page/projects";
    }

    @GetMapping("admin-page/projects/confirmDelete/{id}")   
    public String confirmDelete(@PathVariable("id") Long id, Model model){
        model.addAttribute("projectId" , id);
        return "confirm-project-delete";
    }

    @PostMapping("admin-page/projects/delete/{id}")
    public String deleteProject(@PathVariable("id") Long id){
        projectsServiceImplementation.deleteProject(id);
        return "redirect:/admin-page/projects";
    }
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(RedirectAttributes redirectAttributes) {
        System.out.println("Max Size Caught Exception!!!!");
        redirectAttributes.addFlashAttribute("error", "File is too large. Maximum upload size is 1MB.");
        return "redirect:/admin-page/projects";
    }
}
