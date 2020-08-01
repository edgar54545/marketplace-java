package com.marketplace.users.services;

import com.marketplace.users.domain.Role;
import com.marketplace.users.domain.UserEntity;
import com.marketplace.users.dtos.UserDto;
import com.marketplace.users.repository.UserRepository;
import com.marketplace.users.web.error_handle.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper userToUserDto;
    private final ModelMapper userDtoToUser;

    @Override
    public String addUser(UserDto userDto) {
        UserEntity userEntity = userDtoToUser.map(userDto, UserEntity.class);
        userEntity.setRole(Role.ROLE_COMMON); // Admin role users should be registered manually

        return userRepository.save(userEntity).getUsername();
    }

    @Override
    public UserDto getUserByUserName(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(username));
        return userToUserDto.map(userEntity, UserDto.class);
    }

    @Override
    public void update(String username, UserDto userDto) {
        userRepository.save(userDtoToUser.map(userDto, UserEntity.class));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(user.getUsername(), user.getBCryptPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}
