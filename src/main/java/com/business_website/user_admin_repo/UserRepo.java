package com.business_website.user_admin_repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.business_website.user_admin_model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
    User findByEmail(String email);
}
