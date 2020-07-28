package com.marketplace.users.config;

import com.marketplace.users.domain.User;
import com.marketplace.users.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class MappersConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public ModelMapper userToUserDto() {
        ModelMapper modelMapper = configuredMapper();
        modelMapper.typeMap(User.class, UserDto.class)
                .addMappings(mapping -> mapping.skip(UserDto::setPassword));

        return modelMapper;
    }

    @Bean
    public ModelMapper userDtoToUser() {
        ModelMapper modelMapper = configuredMapper();
        Converter<String, String> passwordEncoder = context -> bCryptPasswordEncoder.encode(context.getSource());
        modelMapper.typeMap(UserDto.class, User.class)
                .addMappings(mapping -> {
                    mapping.skip(User::setCreatedDate);
                    mapping.skip(User::setId);
                    mapping.skip(User::setLastModifiedDate);
                    mapping.skip(User::setRole);
                })
                .addMappings(mapping -> mapping.using(passwordEncoder)
                        .map(UserDto::getPassword, User::setBCryptPassword));

        return modelMapper;
    }

    private ModelMapper configuredMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        return modelMapper;
    }
}
