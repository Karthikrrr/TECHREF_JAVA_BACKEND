package com.business_website.service_implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.business_website.dto.UserDto;
import com.business_website.models.User;
import com.business_website.repository.UserRepo;
import com.business_website.services.UserService;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Override
    public User save(UserDto userDto) {
       User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getCreatedAt() ,userDto.getRole());
        return userRepo.save(user);
    }
    
    @Override
    public boolean checkUsername(String email){
        return userRepo.checkUsername(email);
    }

    @Override
    public List<User> getAllUsers() {
       return userRepo.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow();
        userRepo.delete(user);
    }
}
