package com.marketplace.users.services;

import com.marketplace.users.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    String addUser(UserDto userDto);

    UserDto getUserByUserName(String username);

    void update(String username, UserDto userDto);
}
