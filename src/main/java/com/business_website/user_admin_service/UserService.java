package com.business_website.user_admin_service;

import com.business_website.dto.UserDto;
import com.business_website.user_admin_model.User;

public interface UserService {
    User save(UserDto userDto);
}
