package com.business_website.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.business_website.dto.UserDto;
import com.business_website.models.User;
import com.business_website.service_implementation.UserServiceImplementation;
import com.business_website.services.UserService;

@Controller
public class UserController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @GetMapping("/")
    public String getHome(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("users", userDetails);
        return "home";
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    @PostMapping("/register")
        public String saveUser(@ModelAttribute("users") UserDto userDto, Model model){
            String email =  userDto.getEmail();
            if(userService.checkUsername(email)){
                System.out.println(email + "User exist");
                model.addAttribute("message", "Email Alredy exist");
                return "register";              
            }
            else{
                System.out.println(email + "new User");
                userService.save(userDto);
                model.addAttribute("message", "Register Sucessfull");
                return "login";
            }
        
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }




    //ADMIN Controller
    @GetMapping("/admin-page")
    public String admin(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("users", userDetails);
        return "admin";
    }

    @GetMapping("/admin-page/users")
    public String usersList(Model model){
        List<User> users = userServiceImplementation.getAllUsers();
        model.addAttribute("userList" , users);
        return "admin-user-list";
    }

    @GetMapping("/admin-page/users/confirmDelete/{id}")
    public String confirmDelete(@PathVariable("id") Long id, Model model){
        model.addAttribute("userId", id);
        return "confirm-delete";
    }

    @PostMapping("/admin-page/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userServiceImplementation.deleteUser(id);
        return "redirect:/admin-page/users";
    }
    
}
