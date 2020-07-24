package com.marketplace.users.services;

import com.marketplace.users.domain.ROLE;
import com.marketplace.users.domain.User;
import com.marketplace.users.repository.UserRepository;
import com.marketplace.users.web.error_handle.NotFoundException;
import com.marketplace.users.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public String addUser(UserDto userDto) {
        User user = UserDto.toUser(userDto);
        user.setRole(ROLE.COMMON);

        return userRepository.save(user).getUsername();
    }

    @Override
    public UserDto getUserByUserName(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new NotFoundException(userName));
        return UserDto.fromUser(user);
    }

    @Override
    public void update(String username, UserDto userDto) {
        userRepository.save(UserDto.toUser(userDto));
    }
}
