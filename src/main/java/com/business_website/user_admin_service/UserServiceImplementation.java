package com.business_website.user_admin_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.business_website.dto.UserDto;
import com.business_website.user_admin_model.User;
import com.business_website.user_admin_repo.UserRepo;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Override
    public User save(UserDto userDto) {
       User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getRole());
        return userRepo.save(user);
    }

}
