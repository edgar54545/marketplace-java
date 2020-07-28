package com.marketplace.users.services;

import com.marketplace.users.domain.ROLE;
import com.marketplace.users.domain.User;
import com.marketplace.users.repository.UserRepository;
import com.marketplace.users.web.error_handle.NotFoundException;
import com.marketplace.users.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper userToUserDto;
    private final ModelMapper userDtoToUser;

    @Override
    public String addUser(UserDto userDto) {
        User user = userDtoToUser.map(userDto, User.class);
        user.setRole(ROLE.COMMON); // Admin role users should be registered manually

        return userRepository.save(user).getUsername();
    }

    @Override
    public UserDto getUserByUserName(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new NotFoundException(userName));
        return userToUserDto.map(user, UserDto.class);
    }

    @Override
    public void update(String username, UserDto userDto) {
        userRepository.save(userDtoToUser.map(userDto, User.class));
    }
}
