package com.marketplace.users.services;

import com.marketplace.users.domain.ROLE;
import com.marketplace.users.domain.User;
import com.marketplace.users.repository.UserRepository;
import com.marketplace.users.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User addUser(UserDto userDto) {
        User user = UserDto.toUser(userDto);
        user.setRole(ROLE.COMMON);

        return userRepository.save(user);
    }

    @Override
    public UserDto getUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(RuntimeException::new);
        return UserDto.fromUser(user);
    }
}
