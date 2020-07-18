package com.marketplace.users.services;

import com.marketplace.users.domain.User;
import com.marketplace.users.web.model.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    User addUser(UserDto userDto);

    UserDto getUserByUserName(String username);

    void update(String username, UserDto userDto);
}
