package com.business_website.dto;

import java.time.LocalDateTime;

public class UserDto {

    private String email;
    private String password;
    private LocalDateTime createdAt;
    private String role;
  

    public UserDto(String email, String password,LocalDateTime createdAt, String role) {
        super();
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    

}
